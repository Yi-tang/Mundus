# Mundus
## Membres
TANG Yi, WANG Yijie
- - -
## Choices and protocol of application
* class request 
 * { enum type (signUp,consult,change), object detail}
* class response
 * { type(information; status of modificaition),boolean: flag,string:content}

- - -

#### 1.Sign up(for future Mundus)
* request: {type:signup,detail:student}
 * class Student{String:name,String:mail, Speciality:speciality}
* response: {boolean:flag,string:phrase of success or failure}

#### 2.Practical INFORMATION
#### 3.Speciality INFORMATION
#### 4.Stage INFORMATION
* request: { type: consult, detail:String}
* response: {content}

#### 5.Change info(for responser)
* request: {type:change,detail:String}
* response: {string:content}

#### 6.Quit
