from google.appengine.ext import db
import spreadsheet_updates as gs

'''Using the models in this module we will store the data uploaded from the 
cellphones before sending it to the spreadhseets'''


class Student(db.Model):
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

    @staticmethod
    def get_from_json(json_list):
        '''Takes in a list of json objects and creates a db entry for each.'''
        for obj in json_list:
            entity = Student.get_or_insert(str(obj['id']))
            entity.update(obj)
        
    def update(self, obj):
        '''Updates an existing model according to the values
        passed in by kwargs'''
        for key, value in obj.iteritems():
            setattr(self, key.lower(), str(value))
        self.put()
        gs.update_worksheet_row(self.to_dict(), 'Students')
    
    def to_dict(self):
        ret = {}
        for key, value in self.properties().iteritems():
            ret[key] = getattr(self, key)
        ret["id"] = str(self.id)
        return ret

class Comment(db.Model):
    comment = db.StringProperty()
    activityid = db.StringProperty()
    intime = db.StringProperty()
    lastchangetime = db.StringProperty()
    outtime = db.StringProperty()
    sessionid = db.StringProperty()
    studentid = db.StringProperty()
    userid = db.StringProperty()

    @staticmethod
    def get_from_json(json_list):
        '''Takes in a list of json objects and creates a db entry for each.'''
        for obj in json_list:
            entity = Comment.get_or_insert(str(obj['id']))
            entity.update(obj)
        
    def update(self, obj):
        '''Updates an existing model according to the values
        passed in by kwargs'''
        for key, value in obj.iteritems():
            setattr(self, key.lower(), str(value))
        self.put()
        gs.update_worksheet_row(self.to_dict(), 'Comments')
    
    def to_dict(self):
        ret = {}
        for key, value in self.properties().iteritems():
            ret[key] = getattr(self, key)
        ret["id"] = str(self.id)
        return ret

class Activity(db.Model):
    id = db.StringProperty()
    name = db.StringProperty()
 
    @staticmethod
    def get_from_json(json_list):
        '''Takes in a list of json objects and creates a db entry for each.'''
        for obj in json_list:
            entity = Activity.get_or_insert(str(obj['id']))
            entity.update(obj)
        
    def update(self, obj):
        '''Updates an existing model according to the values
        passed in by kwargs'''
        for key, value in obj.iteritems():
            setattr(self, key.lower(), str(value))
        self.put()
        gs.update_worksheet_row(self.to_dict(), 'Activities')

    def to_dict(self):
        ret = {}
        for key, value in self.properties().iteritems():
            ret[key] = getattr(self, key)
        ret["id"] = str(self.id)
        return ret

class Request(db.Model):
    body = db.TextProperty()
