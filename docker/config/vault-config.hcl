storage "file" {
  path = "/vault/data"
}

listener "tcp" {
  address     = "0.0.0.0:8200"
  tls_disable = true
//   tls_cert_file = "/vault/tls/cert.pem"
//   tls_key_file  = "/vault/tls/privkey.pem"
}

api_addr = "http://127.0.0.1:8200"

disable_mlock = true
ui = true
