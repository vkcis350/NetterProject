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

import spreadsheet_upload as sup
from models import Student 

class MainHandler(webapp.RequestHandler):
    def get(self):
        self.post()

    def post(self):
        'Students, activities, checkins separated by new lines'
        self.splitRequest(self.request.body)
        students = Student.all()
        self.response.out.write(str([i.first_name for i in students]))
    
    def splitRequest(self, req_body):
        logging.debug(req_body)
        req_objs = req_body.splitlines()
        logging.debug(req_objs)
        students = json.loads(req_objs[0])
        #activities = req_objs[1]
        #checkins = req_objs[2]
        Student.get_from_json(students)
    

def main():
    application = webapp.WSGIApplication([('/.*', MainHandler)],
                                         debug=True)
    util.run_wsgi_app(application)

if __name__ == '__main__':
    main()
