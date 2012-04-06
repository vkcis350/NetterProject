import time
import gdata.spreadsheet.service
import logging

EMAIL = 'netterapp@gmail.com'
PASSWORD = 'nettercenterapp'

# Find this value in the url  of the google doc ('key=XXX' and copy XXX below)
SPREADSHEET_KEY= '0Ap3SpWBg9QDYdEQ3Q2RocVpSN2FEN1h3WGx6VXI3QlE'

# All spreadsheets have worksheets. I think worksheet #1 by default always
# has a value of 'od6'
WORKSHEET_ID = 'od6'

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
        logging.info("Insert row succeeded.")
    else:
        logging.info("Insert row failed.")

def send_test():
    sclient = set_up()
    data = prep_testdata()
    entry = sclient.InsertRow(data, SPREADSHEET_KEY, WORKSHEET_ID)
    log_dataupload_status(entry)
