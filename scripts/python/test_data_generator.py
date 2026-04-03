import psycopg2
import psycopg2.extras
import random
import uuid
from datetime import datetime, timedelta
from faker import Faker
from typing import List, Tuple

# ===================== Конфигурация =====================
DB_CONFIG = {
    "dbname": "event_db",
    "user": "postgres",
    "password": "root"
}
SCHEMA = "event"
NUM_POINTS = 20000
NUM_PLACES = 2000000
AVG_REVIEWS_PER_PLACE = 10
BATCH_SIZE_INSERT = 5000
BATCH_SIZE_REVIEWS = 10000


def get_connection():
    conn = psycopg2.connect(**DB_CONFIG)
    psycopg2.extras.register_uuid()
    cur = conn.cursor()
    cur.execute(f"SET search_path TO {SCHEMA}, public;")
    conn.commit()
    cur.close()
    return conn


def generate_unique_coordinates(num_points: int) -> List[Tuple[float, float]]:
    coords_set = set()
    while len(coords_set) < num_points:
        lat = round(random.uniform(47.20000, 47.30600), 6)
        lon = round(random.uniform(39.55555, 39.90500), 6)
        coords_set.add((lat, lon))
    return list(coords_set)


def insert_points(conn, coords: List[Tuple[float, float]]) -> List[uuid.UUID]:
    points_ids = []
    with conn.cursor() as cur:
        for lat, lon in coords:
            pid = uuid.uuid4()
            points_ids.append(pid)
            cur.execute(
                "INSERT INTO points (id, latitude, longitude) VALUES (%s, %s, %s)",
                (pid, lat, lon)
            )
        conn.commit()
    return points_ids


def insert_places(conn, points_ids: List[uuid.UUID], num_places: int) -> List[uuid.UUID]:
    """Генерирует и вставляет места, возвращает список id мест."""
    fake = Faker()
    PLACE_TYPES = [
        "CONCERT_VENUE", "THEATER", "CINEMA", "ART_GALLERY", "MUSEUM",
        "NIGHTCLUB", "BAR_PUB", "BOWLING", "KARAOKE",
        "STADIUM", "GYM", "SWIMMING_POOL",
        "CONFERENCE_HALL", "COWORKING", "BUSINESS_CENTER",
        "PARK", "SQUARE", "URBAN_SPACE",
        "RESTAURANT", "CAFE", "FOOD_COURT", "STREET_FOOD",
        "WEDDING_VENUE", "PHOTO_STUDIO", "LOFT_SPACE", "INDUSTRIAL_VENUE",
        "OTHER"
    ]
    places_ids = []

    with conn.cursor() as cur:
        for i in range(0, num_places, BATCH_SIZE_INSERT):
            batch = []
            for j in range(BATCH_SIZE_INSERT):
                if i + j >= num_places:
                    break
                place_id = uuid.uuid4()
                places_ids.append(place_id)
                owner_id = uuid.uuid4()
                point_id = random.choice(points_ids)
                recommended = random.random() < 0.1
                contacts = generate_contacts(fake)
                batch.append((
                    place_id,
                    f"Place {i + j}",
                    fake.street_address(),
                    random.choice(PLACE_TYPES),
                    0, 0,
                    random.randint(0, 999),
                    owner_id,
                    point_id,
                    recommended,
                    psycopg2.extras.Json(contacts)
                ))
            cur.executemany("""
                INSERT INTO places
                (id, title, address, type, total_rating, reviews_amount,
                 total_visits, owner_id, point_id, recommended, contacts)
                VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
            """, batch)
            conn.commit()
    return places_ids


def generate_reviews_distribution(num_places: int, avg_reviews: float) -> List[int]:
    reviews_per_place = []
    for _ in range(num_places):
        n = max(0, int(random.gammavariate(2, avg_reviews / 2)))
        reviews_per_place.append(n)
    return reviews_per_place


def generate_contacts(fake: Faker) -> dict:
    num_phones = random.randint(1, 3)
    mobile_numbers = [fake.phone_number() for _ in range(num_phones)]

    email = fake.email()
    website_url = fake.url() if random.random() > 0.3 else None

    social = {}
    if random.random() > 0.5:
        social["vk"] = "https://vk.com/" + fake.user_name()
    if random.random() > 0.6:
        social["telegram"] = "https://t.me/" + fake.user_name()
    if random.random() > 0.7:
        social["instagram"] = "https://instagram.com/" + fake.user_name()

    return {
        "mobileNumbers": mobile_numbers,
        "email": email,
        "websiteUrl": website_url,
        "social": social
    }


def build_reviews_data(places_ids: List[uuid.UUID], reviews_per_place: List[int]) -> List[Tuple]:
    fake = Faker()
    now = datetime.now()
    reviews_data = []

    for place_idx, place_id in enumerate(places_ids):
        num_rev = reviews_per_place[place_idx]
        for _ in range(num_rev):
            rate = random.choices(
                [0, 1, 2, 3, 4, 5],
                weights=[0.02, 0.05, 0.1, 0.2, 0.3, 0.33]
            )[0]
            comment = fake.sentence() if random.random() > 0.3 else None
            profile_id = uuid.uuid4()
            created_at = now - timedelta(days=random.randint(0, 730))
            updated_at = created_at + timedelta(days=random.randint(0, 30)) if random.random() > 0.7 else None
            review_id = uuid.uuid4()
            reviews_data.append((review_id, profile_id, rate, comment, created_at, updated_at, place_id))

    return reviews_data


def insert_reviews_and_links(conn, reviews_data: List[Tuple]):
    total = len(reviews_data)
    with conn.cursor() as cur:
        for i in range(0, total, BATCH_SIZE_REVIEWS):
            batch = reviews_data[i:i + BATCH_SIZE_REVIEWS]
            cur.executemany("""
                INSERT INTO reviews (id, profile_id, rate, comment, created_at, updated_at)
                VALUES (%s, %s, %s, %s, %s, %s)
            """, [(r[0], r[1], r[2], r[3], r[4], r[5]) for r in batch])
            conn.commit()
            print(f"Inserted {min(i + len(batch), total)}/{total} reviews")

    with conn.cursor() as cur:
        for i in range(0, total, BATCH_SIZE_REVIEWS):
            batch = reviews_data[i:i + BATCH_SIZE_REVIEWS]
            cur.executemany("""
                INSERT INTO place_reviews (owner_id, review_id)
                VALUES (%s, %s)
            """, [(r[6], r[0]) for r in batch])
            conn.commit()
            print(f"Linked {min(i + len(batch), total)}/{total} reviews")


def update_places_aggregates(conn):
    with conn.cursor() as cur:
        cur.execute("""
            UPDATE places p
            SET
                reviews_amount = agg.review_count,
                total_rating = agg.total_rate
            FROM (
                SELECT
                    pr.owner_id AS place_id,
                    COUNT(r.id) AS review_count,
                    COALESCE(SUM(r.rate), 0) AS total_rate
                FROM place_reviews pr
                JOIN reviews r ON pr.review_id = r.id
                GROUP BY pr.owner_id
            ) agg
            WHERE p.id = agg.place_id
        """)
        conn.commit()
        print(f"Updated {cur.rowcount} places")


def refresh_materialized_view(conn):
    with conn.cursor() as cur:
        cur.execute("REFRESH MATERIALIZED VIEW place_scores")
        conn.commit()
        print("Materialized view place_scores refreshed")


def main():
    print("Generating unique coordinates...")
    coords = generate_unique_coordinates(NUM_POINTS)

    conn = get_connection()
    try:
        print(f"Inserting {NUM_POINTS} points...")
        points_ids = insert_points(conn, coords)

        print(f"Inserting {NUM_PLACES} places...")
        places_ids = insert_places(conn, points_ids, NUM_PLACES)

        print("Generating reviews distribution...")
        reviews_per_place = generate_reviews_distribution(NUM_PLACES, AVG_REVIEWS_PER_PLACE)
        total_reviews = sum(reviews_per_place)
        print(f"Total reviews to generate: {total_reviews}")

        print("Building reviews data...")
        reviews_data = build_reviews_data(places_ids, reviews_per_place)

        print("Inserting reviews and links...")
        insert_reviews_and_links(conn, reviews_data)

        print("Updating places aggregations...")
        update_places_aggregates(conn)

        print("Refreshing materialized view...")
        refresh_materialized_view(conn)

    finally:
        conn.close()

    print("All done!")


if __name__ == "__main__":
    main()
