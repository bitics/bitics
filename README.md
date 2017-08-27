# Bitics: Bitcoin stats

In order to build the docker image for the project, run:
```bash
make image
```

Then, to run the project, run:
```bash
docker-compose up -d
```

That will create two containers: bittics-sink and prometheus. You can then go to
`http://your-docker-machine:9090` and start navigating the prometheus metrics exported.

## Metrics exported

The bittics-sink container exports a bunch of JVM metrics for performance-tuning and
two (for now) metrics from the bitcoin network:

transactions_total: Number of total transactions (counter)
bitcoins_total: Number of total bitcoins in transactions (counter)
