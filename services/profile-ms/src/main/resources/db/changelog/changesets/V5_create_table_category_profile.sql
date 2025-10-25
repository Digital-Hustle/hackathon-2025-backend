create table if not exists profiles_category(
    profile_id UUID references profile(id),
    category_id UUID references category(id),
    primary key(profile_id, category_id)
);