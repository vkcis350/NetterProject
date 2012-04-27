#!/usr/bin/env python
#
# Copyright 2007 Google Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
import simplejson as json
import logging

from google.appengine.ext import webapp
from google.appengine.ext.webapp import util

import gspreadsheets as gs

from models import Student, Activity, Comment

class MainHandler(webapp.RequestHandler):
    def get(self):
        self.post()

    def post(self):
        'Students, activities, checkins separated by new lines'
        self.saveDataFromRequest(self.request.body)
        sup.upload_data()
        self.response.out.write(self.request.body)
    
    def saveDataFromRequest(self, req_body):
        request_objs = req_body.splitlines()
        
        students = json.loads(request_objs[0])
        activities = json.loads(request_objs[1])
        comments = json.loads(request_objs[2])

        Student.get_from_json(students)
        Students.all()
        Comment.get_from_json(comments)
        Activity.get_from_json(activities)
    
        
def main():
    application = webapp.WSGIApplication([('/.*', MainHandler)],
                                         debug=True)
    util.run_wsgi_app(application)

if __name__ == '__main__':
    main()
