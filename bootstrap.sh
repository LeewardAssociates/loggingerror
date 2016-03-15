#!/usr/bin/env bash

# Use single quotes instead of double quotes to make it work with special-character passwords
PASSWORD='12345678'
DATABASE='logerrors'
DATABASE_USER='logging'

# update / upgrade
sudo apt-get update
#sudo apt-get -y upgrade

# install apache 2.5 and php 5.5
sudo apt-get install -y apache2
sudo apt-get install -y php5

# install mysql and give password to installer
sudo debconf-set-selections <<< "mysql-server mysql-server/root_password password $PASSWORD"
sudo debconf-set-selections <<< "mysql-server mysql-server/root_password_again password $PASSWORD"
sudo apt-get -y install mysql-server
sudo apt-get install php5-mysql

# install phpmyadmin and give password(s) to installer
# for simplicity I'm using the same password for mysql and phpmyadmin
sudo debconf-set-selections <<< "phpmyadmin phpmyadmin/dbconfig-install boolean true"
sudo debconf-set-selections <<< "phpmyadmin phpmyadmin/app-password-confirm password $PASSWORD"
sudo debconf-set-selections <<< "phpmyadmin phpmyadmin/mysql/admin-pass password $PASSWORD"
sudo debconf-set-selections <<< "phpmyadmin phpmyadmin/mysql/app-pass password $PASSWORD"
sudo debconf-set-selections <<< "phpmyadmin phpmyadmin/reconfigure-webserver multiselect apache2"
sudo apt-get -y install phpmyadmin

# Setup database
echo creating database $DATABASE
echo creating database $DATABASE
mysql -uroot -p12345678 -e "CREATE DATABASE $DATABASE;"

# Make MySQL external accessible
echo `mysql -uroot -p$PASSWORD -e "GRANT ALL PRIVILEGES ON *.* TO '$DATABASE_USER'@'%' IDENTIFIED BY '$DATABASE_USER';"`
echo `mysql -uroot -p$PASSWORD -e "GRANT ALL PRIVILEGES ON *.* TO '$DATABASE_USER'@'localhost' IDENTIFIED BY '$DATABASE_USER';"`
echo `mysql -uroot -p$PASSWORD -e "GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY '$PASSWORD';"`
echo `mysql -uroot -p$PASSWORD -e "GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost' IDENTIFIED BY '$PASSWORD';"`
sed -i 's/^bind-address/#bind-address/' /etc/mysql/my.cnf
sed -i 's/^skip-external-locking/#skip-external-locking/' /etc/mysql/my.cnf
sudo service mysql restart

# Import bootstrap SQL
echo importing database objects
mysql -uroot -p$PASSWORD DATABASE < /sql/create.sql

# setup hosts file
VHOST=$(cat <<EOF
<VirtualHost *:80>
    DocumentRoot "/var/www/html"
    <Directory "/var/www/html">
        AllowOverride All
        Require all granted
    </Directory>
</VirtualHost>
EOF
)
echo "${VHOST}" > /etc/apache2/sites-available/000-default.conf

# enable mod_rewrite
sudo a2enmod rewrite

# restart apache
service apache2 restart

# activate mcrypt
cd /etc/php5/mods-available
sudo php5enmod mcrypt

# restart apache
service apache2 restart