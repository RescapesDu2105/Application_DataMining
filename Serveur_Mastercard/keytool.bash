mkdir CA
cd CA
mkdir certs
mkdir crl
mkdir newcerts
echo > index.txt
echo 01 > serial
openssl 
genrsa -out ca.key 1024
req -new -x509 -key ca.key -out CA/cacert.pem
set PATH=C:\Program Files\Java\jdk1.8.0_45\bin;%PATH%
keytool -genkey -alias TussetDimartino -keystore client_keystore
keytool -genkey -alias TussetDimartino -keystore serveur_keystore
keytool -certreq -alias TussetDimartino -keystore client_keystore -file ServeurPayment.csr -keypass 123Soleil -storepass 123Soleil -v
ca -in ServeurPayment.csr -out ServeurPayment.pem -keyfile ca.key
