from google.appengine.ext import db

'''Using the models in this module we will store the data uploaded from the 
cellphones before sending it to the spreadhseets'''


class BaseModel(db.Model):
    '''This model contains basic functionality shared by all db models in this
    module'''

    @staticmethod
    def get_from_json(json_list):
        '''Takes in a list of json objects and creates a db entry for each.'''
        for obj in json_list:
            entity = get_or_insert(str(obj['id']))

            for key, value in json_list.iteritems():
                key = key.lower()
                setattr(entity, key, str(value))
                entity.put()
    
    def update(self, **kwargs):
        '''Updates an existing model according to the values
        passed in by kwargs'''
        for key in kwargs:
            setattr(self, key, kwargs[key])
        self.put()


class Student(BaseModel):
    id = db.StringProperty()   
    address = db.StringProperty()
    contact = db.StringProperty()
    contactrelation = db.StringProperty()
    firstname = db.StringProperty()
    phone = db.StringProperty()
    lastname = db.StringProperty()
    siteid = db.StringProperty()
    schoolid = db.StringProperty()
    schoolyear = db.StringProperty()
    grade = db.StringProperty()


class Comment(db.Model):
    comment = db.StringProperty()
    activityid = db.StringProperty()
    intime = db.StringProperty()
    lastchangetime = db.StringProperty()
    outtime = db.StringProperty()
    sessionid = db.StringProperty()
    studentid = db.StringProperty()
    userid = db.StringProperty()


class Activity(db.Model):
    id = db.StringProperty()
    name = db.StringProperty()
