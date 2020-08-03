#!/usr/bin/env bash

set -euo pipefail

mongodump --db=k3menudatastore --out=.

DATE_FOR_NAME=$(date '+%Y-%m-%d')
DUMP_NAME="dump_k3menudatastore_${DATE_FOR_NAME}.tar.gz"

tar -czvf $DUMP_NAME ./k3menudatastore

rm -rf ./k3menudatastore

# https://developers.google.com/identity/protocols/oauth2/web-server#obtainingaccesstokens <- Info to obtain access_token
G_CLIENT_ID='637846255671-8c8q5akupisvqc84fofacvil50oo6452.apps.googleusercontent.com'
G_CLIENT_SECRET='KtJdDVenaRnt-lFOeWvec5X5'
G_API_KEY='AIzaSyAW25gI3Bsy0cnQP98Zbe-LfpP9oCVDU6o'

# Copy and paste this in browser for get authorization code
BROWSER_URL="https://accounts.google.com/o/oauth2/v2/auth?client_id=${G_CLIENT_ID}&access_type=offline&redirect_uri=urn:ietf:wg:oauth:2.0:oob&scope=https://www.googleapis.com/auth/drive.file&response_type=code"
AUTHORIZATION_CODE="4/2gFGUcAb9pt-QhN-cLZO1nXviFjr924MEQgirrf6LL5lIclv8NGbVa0"

#ACCESS_TOKEN_RESPONSE=$(curl -s --request POST --data "code=${AUTHORIZATION_CODE}&client_id=${G_CLIENT_ID}&client_secret=${G_CLIENT_SECRET}&redirect_uri=urn:ietf:wg:oauth:2.0:oob&grant_type=authorization_code" https://oauth2.googleapis.com/token | /usr/bin/jq --raw-output '.')
REFRESH_TOKEN="1//03TpOC7W3oj4GCgYIARAAGAMSNwF-L9IrShkcGJYGDmIhHQvqTUha_DwNcuSIUKxQtAb4ZO_PpsIWpiiCfUsghGxp0rd0_OawN64"

# if [[ -z "${ACCESS_TOKEN}" || "${ACCESS_TOKEN}" == "null" ]]; then
ACCESS_TOKEN_RESPONSE=$(curl -s --request POST --data "client_id=${G_CLIENT_ID}&client_secret=${G_CLIENT_SECRET}&refresh_token=${REFRESH_TOKEN}&grant_type=refresh_token" https://oauth2.googleapis.com/token | /usr/bin/jq --raw-output '.')
ACCESS_TOKEN=$(echo $ACCESS_TOKEN_RESPONSE | jq .access_token)
# fi

curl -s -X POST -L \
    -H "Authorization: Bearer ${ACCESS_TOKEN}" \
    -F "metadata={name : '${DUMP_NAME}'};type=application/json;charset=UTF-8" \
    -F "file=@${DUMP_NAME};type=application/gzip" \
    "https://www.googleapis.com/upload/drive/v3/files?uploadType=multipart"