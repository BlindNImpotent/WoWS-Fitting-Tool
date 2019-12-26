# Tech
## [Amazon Web Service (AWS)](https://aws.amazon.com/)
* Elastic Beanstalk (EB)
    * General management for resources tied to server instance for WoWSFT.
    * Resources are tweaked accordingly.
* Elastic Load Balancer (ELB)
    * (Workload distribution and) HTTPS handling before requests/responses are sent to/from server instance(s).
    * Redirects HTTP and enforces HTTPS.
* Simple Storage Service (S3)
    * Stores original files that are to be distributed to Content Delivery Network (CDN).
    * Other uses include serving static web page in case of maintenance.
* CloudFront
    * CDN. Settings include distribution location, access restriction and caching.
* Route 53
    * DNS. Verifies and connects resource endpoint inside AWS with purchased domain.