#!/usr/bin/env sh

# Database variables
# Note: make sure that your password matches what is in the docker-compose.yml
DB_HOSTNAME=localhost
DB_USER=sa
DB_PASSWORD=p@sswordSA

# Wait to be sure that SQL Server came up
sleep 30s

# Run the setup script to create the DB and the schema in the DB
/opt/mssql-tools/bin/sqlcmd -S ${DB_HOSTNAME} -U ${DB_USER} -P ${DB_PASSWORD} -d master -i /app/initial-data.sql
