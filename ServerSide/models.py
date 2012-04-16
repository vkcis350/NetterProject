from google.appengine.ext import db

'''Using the models in this module we will store the data uploaded from the 
cellphones before sending it to the spreadhseets'''

class Student(db.Model):
   
    address = db.StringProperty()
    contact = db.StringProperty()
    contactiRelation = db.StringProperty()
    firstName = db.StringProperty()
    phone = db.StringProperty()
    lastName = db.StringProperty()
    siteID = db.IntegerProperty()
    schoolID = db.IntegerProperty()
    schoolYear = db.IntegerProperty()
    grade = db.IntegerProperty()

    @staticmethod
    def get_from_json(json_list):
        '''Takes in a list of json objects and creates a db entry for each.'''
        for student in json_list:
            entity = Student.get_or_insert(str(student['id']))
            
            entity.address = student['address']
            entity.contact = student['contact']
            entity.contact_relation = student['contactRelation']
            entity.first_name = student['firstName']
            entity.phone = student['phone']
            entity.last_name = student['lastName']
            entity.site_id = student['siteID']
            entity.school_id = student['schoolID']
            entity.school_year = student['schoolYear']
            entity.grade = student['grade']
            entity.put()
   

class Comment(db.Model):
    comment = db.StringProperty()
    activityID = db.StringProperty()
    inTime = db.IntegerProperty()
    lastChangeTime = db.IntegerProperty()
    outTime = db.IntegerProperty()
    sessionID = db.IntegerProperty()
    studentID = db.IntegerProperty()
    userID = db.IntegerProperty()

    @staticmethod
    def get_from_json(json_list):
        for comment in comments:
            entity = Comment.get_or_insert(str(comment['id']))
            
            entity.comment = comment['comment']
            entity.activityID = comment['activityID']
            entity.inTime = comment['inTime']
            entity.lastChangeTime = comment['lastChangeTime']
            entity.outTime = comment['outTime']
            entity.sessionID = comment['sessionID']
            entity.studentID = comment['studentID']
            entity.userID = comment['userID']
            entity.put()


class Activity(db.Model):
    name = db.StringProperty()

    @staticmethod
    def from_json_list(json_list):
        for activity in json_list:
            entity = Activity.get_or_insert(str(comment['id']))

            entity.name = activity['name']
            entity.put()

class Request(db.Model):
    '''User to record body of requests sent. For testing purposes only'''
    body = db.TextProperty()
