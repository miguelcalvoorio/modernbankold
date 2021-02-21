## Thanks to Abhishek koserwal
## https://medium.com/keycloak/keycloak-jwt-token-using-curl-post-72c9e791ba8c

if [ $# -ne 2 ]; then
  echo 1>&2 "Usage: . $0 username\n"
  
  return
fi

USERNAME=$1
HOSTNAME="localhost:8180"
REALM_NAME="modernbank"
CLIENT_ID="internetFrontEnd"

KEYCLOAK_URL=http://$HOSTNAME/auth/realms/$REALM_NAME/protocol/openid-connect/token

echo "Using Keycloak: $KEYCLOAK_URL"
echo "realm: $REALM_NAME"
echo "client-id: $CLIENT_ID"
echo "username: $USERNAME"

echo -n Password: 
read -s PASSWORD


export TOKEN=$(curl -X POST "$KEYCLOAK_URL" --insecure \
 -H "Content-Type: application/x-www-form-urlencoded" \
 -d "username=$USERNAME" \
 -d "password=$PASSWORD" \
 -d 'grant_type=password' \
-d "client_id=$CLIENT_ID" | jq -r '.access_token')

echo $TOKEN


