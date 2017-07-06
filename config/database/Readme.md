


### Some instructions

* Note - you may need to run the following commands as the postgres user; i.e: `sudo -su postgres psql ...etc`

* Note 2 - It may be better to simply log into the `psql` with `sudo -u postgres psql postgres`, then simply then use the `\i` switch to execute from sql files: i.e: `\i /opt/srv/runkeeper-site/config/database/0.database.sql`



#### Create the database
##### Unix
```psql -U postgres postgres -f C:\Development\Git\personal\nevex-and-runkeeper\config\database\0.database.sql```

Or in `psql`
```\i /path/to/0.database.sql```

##### Windows
```psql -U postgres -f C:\Development\Git\personal\nevex-and-runkeeper\config\database\0.database.sql postgres```


#### Create the schema

```psql -h localhost -d runkeeper -U postgres -p 5432 -a -f C:\Development\Git\personal\nevex-and-runkeeper\config\database\1.schema.sql```

Or in `psql`
* ```\connect runkeeper```

* ```\i /path/to/0.database.sql```

#### Create the tables 

```psql -h localhost -d runkeeper -U postgres -p 5432 -a -f C:\Development\Git\personal\nevex-and-runkeeper\config\database\2.CommentsJobs.sql```

Or in `psql`
```\i /path/to/0.database.sql```

