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

from google.appengine.ext import webapp
from google.appengine.ext.webapp import util

import gspreadsheet as gs

class MainHandler(webapp.RequestHandler):
    def get(self):
        gs.send_test()
        self.response.out.write('''<html>
                                    <body>
                                        <h1>Data has been input into the
        <a href="https://docs.google.com/spreadsheet/ccc?key=0Ap3SpWBg9QDYdEQ3Q2RocVpSN2FEN1h3WGx6VXI3QlE&pli=1#gid=0" >spreadsheet</a></h1>                                
                                    </body>                              
                                </html>''')

    def formatAsRow(self, json_obj):
        pass

def main():
    application = webapp.WSGIApplication([('/.*', MainHandler)],
                                         debug=True)
    util.run_wsgi_app(application)


if __name__ == '__main__':
    main()
