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
        '''Takes in a list of json objects and creates a db entry.
        {"address":"Versailles?","contact":"Duke of Wellington","contactRelation":"Arch
        Nemesis","firstName":"Napoleon","phone":"411","lastName":"Bonaparte","siteID":0,
        "schoolID":0,"schoolYear":1800,"grade":6,"id":2}'''
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
    pass

class Activity(db.Model):
    pass

class Request(db.Model):
    body = db.TextProperty()
