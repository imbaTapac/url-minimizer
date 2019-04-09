# Welcome to URL minimizer! 
### All table creates automatic by Hibernate.
#### API starts with /api/v1/minimizer
##### 1. @POST /api/v1/minimizer/url/create - creating mini-url
##### 2. @GET /api/v1/minimizer/url/all - retrieves all urls with pageable options
##### 3. @GET /api/v1/minimizer/{minimizedURL} - redirect user to original url and write statistics fro this URL
##### 4. @GET /api/v1/minimizer/statistic/{minimizedURL} - retrieves statistic for URL ( redirect (overall and for browser type))
##### 5. @PUT /api/v1/minimizer/update - update mini-url with related props
##### 6. @POST /api/v1/minimizer/{minimizedURL}/status/{action} - change active status of URL by action ACTIVATE, DEACTIVATE
##### 7. @DELETE /api/v1/minimizer/{minimizedURL} - delete url
