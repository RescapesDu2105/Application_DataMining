mkdir CA
cd CA
mkdir certs
mkdir crl
mkdir newcerts
echo > index.txt
echo 01 > serial
# openssl 
# genrsa -out ca.key 1024
# req -new -x509 -key ca.key -out CA/cacert.pem
# exit
# keytool -genkey -alias TussetDimartino -keystore ServeurPayment
# keytool -genkey -alias TussetDimartino -keystore ServeurMastercard
# keytool -certreq -alias TussetDimartino -keystore ServeurPayment -file ServeurPayment.csr -keypass 123Soleil -storepass 123Soleil -v
# openssl
# ca -in ServeurPayment.csr -out ServeurPayment.pem -keyfile ca.key
# x509 -in ServeurPayment.pem -out ServeurPayment.der -outform DER
# exit
# keytool -import -v -alias certificauthority -file CA\cacert.pem -keystore ServeurPayment -storepass 123Soleil
# keytool -import -v -keystore ServeurPayment -alias TussetDimartino -file ServeurPayment.der -storepass 123Soleil
# keytool -list -keystore ServeurPayment
# keytool -list -v -keystore ServeurMastercard
# openssl
# ca -in ServeurMastercard.csr -out ServeurMastercard.pem -keyfile ca.key
# x509 -in ServeurMastercard.pem -out ServeurMastercard.der -outform DER
# exit
# keytool -import -v -alias certificauthority -file CA\cacert.pem
# keytool -import -v -keystore ServeurMastercard -alias TussetDimartino –file ServeurMastercard.der -storepass 123Soleil
# keytool -list -keystore ServeurMastercard