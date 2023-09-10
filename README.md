# TrailAnywhere

## Introduction

TrailAnywhere is an app to help users search for hiking trails. Using a zip code, longitude/latitude coordinates, difficulty rating, or
type of trail, the app will search for a list of trails that fall within the given user input.

Other features will include adding new trails, adding alerts to a trail, like Waze app alerts, and seeing what the current weather report is based on a 
trails zip code. 

Because TrailAnywhere is a microservice, users can interact with this application using either a set of RESTFul service endpoints, a simple UI, or both.

## Storyboard

![TrailAnywhere Invision Storyboard](https://github.com/wrytherUC/TrailAnywhere/blob/8995ce845fea8213f5657a46ba2054047ff073e2/TrailAnywhere-InvisionStoryboard.png)

[TrailAnywhere Storyboard in Invision](https://nate965053.invisionapp.com/freehand/TrailAnywhere-BAHo9RwnD)

## Requirements

### 1. Hikers would like the ability to search for information about hiking trails.

### Example

**Given**: Trail data is available and there are 2 trails rated “Difficult”

**When**: The user searches for trails with the constraint “Difficult”

**Then**: TrailAnywhere returns 2 hiking trails, rated “Difficult”

### Example

**Given**: Trail data is available

**When**: The User searches “Bajs784jds” in the trail field

**Then**: TrailAnywhere does not return results

### Example

**Given**: Trail data is available and has a "Forrest Park" entry

**When**: The user searches for “Forest Park”

**Then**: TrailAnywhere returns information/attributes for “Forest Park”

### Example

**Given**: Trail data is available, “Mount Airy” is the only trail in the zip code 45239

**When**: The User enters “45239” in the zip code field

**Then**: TrailAnywhere returns one trail, “Mount Airy”

### 2.	Hikers would like to know about weather details for trails found based on search parameters.

### Example

**Given**: Trail data is available with a zip code attribute

**When**: User enters in information to find a trail based on zip code, 45239

**Then**: Weather information is provided below the trail results for area in zip code 45239

### Example

**Given**: Trail data is available with a longitude/latitude attribute

**When**: User enters in information to find a trail based on longitude/latitude 39.19937,-84.57534

**Then**: A service will convert the longitude/latitude to a zip code

**Then**: Weather information will be provided based on the zip code, 45239

### 3.  Hikers would like to search for and add alerts relating to specific trails.

### Example

**Given**: UserA is logged in, and trail “Sharon Woods” data is available

**When**: UserA submits an alert for “Flooding” along the “Sharon Woods” trail

**Then**: TrailAnywhere adds UserA’s “Flooding” alert to “Sharon Woods”.

**Then**: TrailAnywhere displays “Flooding” alert to other users looking for “Sharon Woods”

#### Example

**Given**: UserB is logged in, and trail “French Park” data is available

**When**: UserB submits an alert that contains bad language for trail “French Park”

**Then**: The TrailAnywhere app will show an error prompt saying "No profane language is allowed for trail alerts!," when UserB hits the submit button.

## Class Diagram

![TrailAnywhere UML Diagram](https://github.com/wrytherUC/TrailAnywhere/blob/8995ce845fea8213f5657a46ba2054047ff073e2/Final%20Project%20-%20Class%20Diagram.png)

[TrailAnywhere Diagram](https://mailuc-my.sharepoint.com/:u:/g/personal/oterokh_mail_uc_edu/EWVWzCi2ScVNl_gkUfbfb9gBpNXzSorhuGdV_UclySsCHw?e=8GpgNy)

### Class Diagram Description

- Enterprise Application - Will start the TrailAnywhere application
- Controllers - Decides what to render when a user hits a URL/Endpoint
- Trails Service - Will create, list trails
- Alerts Service - Will create trail alerts for specific trials
- Users Service - Users can log in to perform tasks like create a trail or alert
- Trails DTO - Noun class to represent a trail
- Alerts DTO - Noun class to represent an alert
- Users DTO - Noun class to represent an end user
- Trails DAO - Interface for trail CRUD operations
- Alerts DAO - Interface for alert CRUD operations
- Users DAO - Interface for user CRUD operations

## JSON Schema

- This JSON schema is from our Trail class and what we plan to export for another app.
- The JSON schema generated using this [repo](https://github.com/victools/jsonschema-generator).


> {
>  "type" : "object",
>  "properties" : {
>    "address" : {
>      "type" : "string"
>    },
>    "difficulty" : {
>      "type" : "string"
>    },
>    "latitude" : {
>      "type" : "string"
>    },
>    "longitude" : {
>      "type" : "string"
>    },
>    "name" : {
>      "type" : "string"
>    },
>    "terrain" : {
>      "type" : "string"
>    },
>    "trailType" : {
>      "type" : "string"
>    },
>    "trailid" : {
>      "type" : "integer"
>    },
>    "zipCode" : {
>      "type" : "integer"
>    }
>  }
>}

## Team Memebers and Roles

UI Specialist: Nate Virgin  
Business Logic/Persistence: Kenneth Otero  
Database/Data Engineer, Role Floater: Kayla Neely  
DevOps/Product Owner/Scrum Master/GitHub Admin: Wesley Ryther

## GitHub Project

[TrailAnywhere Repository](https://github.com/wrytherUC/TrailAnywhere)

## Milestones

[Milestone 1](https://github.com/wrytherUC/TrailAnywhere/milestone/1)

## Standup

[6:30 PM EST Thursdays on Teams](https://teams.microsoft.com/l/meetup-join/19%3ameeting_NjQ4NmQ2MjgtYWE0Zi00OTgzLTg2YjMtNWFlYTNkMjdkYjNk%40thread.v2/0?context=%7b%22Tid%22%3a%22f5222e6c-5fc6-48eb-8f03-73db18203b63%22%2c%22Oid%22%3a%2259195007-8da2-458f-bfcc-0bc8c21540dd%22%7d)


