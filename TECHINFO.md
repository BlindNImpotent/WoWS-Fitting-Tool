# Tech
## [Amazon Web Service (AWS)](https://aws.amazon.com/)
##### Elastic Beanstalk (EB)
* Management of AWS resources associated with WoWSFT web application.
* Each resource can be tweaked accordingly and/or individually when needed.

##### Elastic Load Balancer (ELB)
* HTTPS encrypt/decrypt process in front of EB. Highly recommended because private key should always be kept safe and hidden away.
* Can also be used to distribute traffic and workload between instances.
* Redirects HTTP requests to use HTTPS, while enforcing HTTPS.

##### Simple Storage Service (S3)
* Stores original files that are to be distributed to Content Delivery Network (CDN).
* Other uses include serving static web page in case of maintenance.

##### CloudFront
* CDN. Settings include distribution location, access restriction and caching.

##### Route 53
* DNS. Verifies and connects resource endpoint inside AWS with purchased domain.