import time
import gdata.spreadsheet.service
import logging

from models import Student, Activity, Comment

EMAIL = 'netterapp@gmail.com'
PASSWORD = 'nettercenterapp'

# Find this value in the url  of the google doc ('key=XXX' and copy XXX below)
SPREADSHEET_KEY= '0Ap3SpWBg9QDYdEQ3Q2RocVpSN2FEN1h3WGx6VXI3QlE'

# All spreadsheets have worksheets. I think worksheet #1 by default always
# has a value of 'od6'
WORKSHEET_IDS = ['0', '1', '2']
MODELS = [Student, Activity, Comment]

def set_up():
    spr_client = gdata.spreadsheet.service.SpreadsheetsService()
    spr_client.email = EMAIL
    spr_client.password = PASSWORD
    spr_client.source = 'Example Spreadsheet Writing Application'
    spr_client.ProgrammaticLogin()
    return spr_client

def prep_testdata():
# Prepare the dictionary to write
    dict = {}
    dict['date'] = time.strftime('%m/%d/%Y')
    dict['time'] = time.strftime('%H:%M:%S')
    dict['random'] = "Testing" 
    return dict

def log_dataupload_status(entry):
    if isinstance(entry, gdata.spreadsheet.SpreadsheetsList):
        print "Insert row succeeded."
    else:
        logging.info("Insert row failed.")

def upload_data():
    sclient = set_up()
    ctr = 0
    for wid in WORKSHEET_IDS:
        data = MODELS[0].all()
        ctr += 1
        for datum in data:
            properties = properties_to_string(datum.properties())
            entry = sclient.InsertRow(properties, SPREADSHEET_KEY, wid)
            log_dataupload_status(entry)

def properties_to_string(model_dict):
    properties = {}
    for key in model_dict:
        properties[key] = str(model_dict[key])
    return properties
