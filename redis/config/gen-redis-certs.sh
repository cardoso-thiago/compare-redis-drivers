generate_cert() {
    local name=$1
    local cn="$2"
    local opts="$3"

    local keyfile=tls/${name}.key
    local certfile=tls/${name}.crt

    [ -f $keyfile ] || openssl genrsa -out $keyfile 2048
    openssl req \
        -new -sha256 \
        -subj "/O=Redis Test/CN=$cn" \
        -key $keyfile | \
        openssl x509 \
            -req -sha256 \
            -CA tls/ca.crt \
            -CAkey tls/ca.key \
            -CAserial tls/ca.txt \
            -CAcreateserial \
            -days 365 \
            $opts \
            -out $certfile
}

mkdir -p tls
[ -f tls/ca.key ] || openssl genrsa -out tls/ca.key 4096
openssl req \
    -x509 -new -nodes -sha256 \
    -key tls/ca.key \
    -days 3650 \
    -subj '/O=Redis Test/CN=Certificate Authority' \
    -out tls/ca.crt

cat > tls/openssl.cnf <<_END_
[ server_cert ]
keyUsage = digitalSignature, keyEncipherment
nsCertType = server
[ client_cert ]
keyUsage = digitalSignature, keyEncipherment
nsCertType = client
_END_

generate_cert server "Server-only" "-extfile tls/openssl.cnf -extensions server_cert"
generate_cert client "Client-only" "-extfile tls/openssl.cnf -extensions client_cert"
generate_cert redis "localhost"

[ -f tls/redis.dh ] || openssl dhparam -out tls/redis.dh 2048