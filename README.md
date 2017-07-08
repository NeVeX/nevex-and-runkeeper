# nevex-and-runkeeper
Making an inspirational bot tracker to inspire people when they run using Runkeeper

#### GCloud

* To list your services in gcloud; use cmd prompt and type `gcloud projects list`

#### Deploying

* In a cmd prompt (presuming gcloud sdk is installed etc)
  * `gcloud init`
  * Pick `1`
  * Pick the corresponding project to use
  * You should be good - now you can use maven `gcloud deploy`

#### Release notes

##### `1.0.1`

* Disabling the cron job since the new `2.0.0` release is going live

##### `1.0.0`

* First release that lives on google cloud
