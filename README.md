# User Profile Management
This is an API for User Profile Management built on Springboot.

This project has a built-in Oauth Authentication server  that should then be utilized to access the APIs.
Inside the CustomUserDetailsService is the creation of test users (You can edit this or better yet link this to an actual User Repo). 

    username : user1
    password : password1

    username : user2
    password : password2




#### Get token from Authentication Server

Launch POSTMAN to generate TOKEN

Authorization Tab
![oauth-authorization-1](https://user-images.githubusercontent.com/39042426/53307699-5a030f80-38ef-11e9-8b24-0379eff2c292.png)

Body Tab
![oauth-authorization-2](https://user-images.githubusercontent.com/39042426/53307753-b23a1180-38ef-11e9-97c6-15e35469ae50.png)

Response 
![oauth-authorization-3](https://user-images.githubusercontent.com/39042426/53307759-caaa2c00-38ef-11e9-8fe7-d79671135e8e.png)



#### Simple Post Call to Create a User Profile

This will require the access token generated from the Response from the Authentication Server

Body Tab
![insertuserprofile-1](https://user-images.githubusercontent.com/39042426/53307761-dac20b80-38ef-11e9-9fc4-d3999379508a.png)

Headers Tab
![insertuserprofile-2](https://user-images.githubusercontent.com/39042426/53307772-e9a8be00-38ef-11e9-85ab-7179aed2e755.png)

Params Tab
![insertuserprofile-3](https://user-images.githubusercontent.com/39042426/53307779-f4fbe980-38ef-11e9-96d7-4ecbae9a300b.png)

This API connects to an AWS RDS. 

Full Details of the User Profile API is documented on the yaml file (documentation folder) 

