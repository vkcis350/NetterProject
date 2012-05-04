from gspreadsheet import SpreadsheetAPI
import logging

EMAIL = 'netterapp@gmail.com'
PASSWORD = 'nettercenterapp'
APP_NAME = 'nettercenter350'
SOURCE = 'Netter Center Android App'

# Find this value in the url  of the google doc ('key=XXX' and copy XXX below)
SPREADSHEET_KEY= '0Ap3SpWBg9QDYdEQ3Q2RocVpSN2FEN1h3WGx6VXI3QlE'
SPREADSHEET_NAME = 'Android App Data'
   
def update_worksheet_row(model_info, worksheet_name):
    '''Takes a dict containing a db model's fields and fills the spreadsheet 
    row according to the model's fields.'''
    worksheet = get_worksheet(worksheet_name)
    rows = worksheet.get_rows()
    row_index = get_row_index_by_id(rows, model_info["id"])

    if row_index is not None:
        worksheet.update_row(row_index, model_info)
    else:
        worksheet.insert_row(model_info)
   
def update_db_model(db_model, worksheet_name):
    '''Takes a model and pulls data from the worksheet'''
    worksheet = get_worksheet(worksheet_name)
    rows = worsheet.get_rows()
    row_index = get_row_index_by_id(rows, db_model.id)
    
    if row_index is not None:
        row = rows[row_index]
        db_model.update(row)
    else:
        #If a corresponding row couldnt be found, input the model into the
        #worksheet
        worksheet.insert_row(db_model.properties())

def get_worksheet(worksheet_name):
    '''Takes a worksheet name and given the default spreadsheet for the app
    and returns the worksheet'''
    #Establish connection
    api = SpreadsheetAPI(EMAIL, PASSWORD, SOURCE)
    #Get worksheets from app spreadsheet
    worksheets = dict(api.list_worksheets(SPREADSHEET_KEY))
    return api.get_worksheet(SPREADSHEET_KEY, worksheets[worksheet_name])
 
def get_row_index_by_id(rows, id):
    '''Returns the index of the row with id, None if not found.'''
    ctr = 0
    for row in rows:
        if str(row["id"]) == str(id): 
            return ctr
        ctr += 1


