#!/usr/bin/env sh

# Run Microsoft SQl Server and initialization script (at the same time)
sh /app/run-initialization.sh & 

/opt/mssql/bin/sqlservr
