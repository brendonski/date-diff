## date-diff API

The date-diff API is provided by a [Micronaut](https://micronaut.io) Java application.

To run the application from source

`./gradlew run`

or from a downloaded ZIP package

```
unzip date-diff.zip
java -jar datediff-0.1-all.jar
```

A server will start at http://localhost:8080

### Endpoints

#### Days

Calculate complete days between two date-time parameters. Optionally specify a unit to convert days into another unit.

```
GET /date-diff/days?
```

```json
{
  "days":1,
  "convert":{
    "value":1440,
    "unit":"MINUTES",
  },
}
```

#### Weekdays

Calculate the number of week days (i.e. Mon-Fri) between `d1` and `d2`. Optionally specify a unit `u` to convert days into another unit.

```
GET /date-diff/weekdays?
```

```json
{
  "days":1,
  "convert":{
    "value":1440,
    "unit":"MINUTES"
  },
}
```

#### Weeks

Calculate the number of complete weeks between `d1` and `d2`. Optionally specify a unit `u` to convert days into another unit. A complete week is calculated from the system default locale. E.g. the first day of the week may be Sunday or Monday, depending on the locale.

The number of weeks is calculated as the number of complete week blocks between the dates e.g. Sunday - Saturday. Partial weeks are not included.

```
GET /date-diff/weekdays?
```

```json
{
  "weekDays":5,
  "convert": {
    "value":120,
    "unit":"HOURS"
  },
}
```

#### Parameters for all endpoints

```
d1=31-10-2020 01:30:00 (mandatory)
d2=01-11-2020 12:30:00 (mandatory)
u=SECONDS (optional, valid values SECONDS, MINUTES, HOURS, YEARS)
tz1=Europe/Paris (optional, valid timezone for d1)
tz2=Australia/Adelaide (optional, valid timezone for d2)
```

`d1` does not need to be before `d2`. The date-times can be supplied in any order. The result for all endpoints will always be positive numbers. An error occurs if either `d1` or `d2` is not supplied.

If either time zone `tz1` or `tz2` is not supplied, the system default time zone will be used. See https://en.wikipedia.org/wiki/List_of_tz_database_time_zones for a list of valid timezones

If `u` is supplied, the converted unit will be whole units of seconds, minutes, hours or years. Years will be rounded down to the number of whole years, based on 365 days.