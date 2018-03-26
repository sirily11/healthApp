from flask import Flask, request, redirect, url_for, flash
import sqlite3
import json

app = Flask(__name__)
"""
Database name
"""
TABLE_NAME = "HEALTH_DATA"
database_name = 'test.db'

"""
Column name
"""
ACCOUNT_ID = 'ACCOUNT_ID'
PROFILE_NAME = "PROFILE_NAME"
STEPS = "STEPS"
CAL_BURNED = "CAL_BURNED"
CAL_INTAKE = "CAL_INTAKE"
FOOD_MAP = "FOOD_MAP"
ABOUTME_DESCRIBE = "ABOUTME_DESCRIBE"
FRIEND_LIST = "FRIEND_LIST"
PROFILE_PIC = "PROFILE_PIC"


def onCreate():
    conn = sqlite3.connect("test.db", check_same_thread=False)
    c = conn.cursor()
    c.execute("CREATE TABLE IF NOT EXISTS {} ({} TEXT UNIQUE NOT NULL , {} TEXT, {} TEXT, {} TEXT, {} TEXT,"
              "{} TEXT, {} TEXT, {} TEXT,{} TEXT)"
              .format(TABLE_NAME,ACCOUNT_ID, PROFILE_NAME,STEPS,CAL_BURNED,CAL_INTAKE,FOOD_MAP,
                      ABOUTME_DESCRIBE,FRIEND_LIST,PROFILE_PIC))
    c.close()
    conn.close()

def insert_account_id(account_id):
    conn = sqlite3.connect(database_name, check_same_thread=False)
    c = conn.cursor()
    c.execute("INSERT OR IGNORE INTO {} ({}) VALUES (?)".format(TABLE_NAME,ACCOUNT_ID),(account_id,))
    conn.commit()
    return c.rowcount == 1

def update_profile_name(account_id,profile_name):
    conn = sqlite3.connect(database_name, check_same_thread=False)
    c = conn.cursor()
    if(account_id is not None):
        data = profile_name[1:-1]
        c.execute("UPDATE {} SET {} = '{}' WHERE {} = '{}'"
                  .format(TABLE_NAME,PROFILE_NAME,data,ACCOUNT_ID,account_id))
        conn.commit()
        return c.rowcount == 1

def update_aboutme(account_id,aboutme):
    conn = sqlite3.connect(database_name, check_same_thread=False)
    c = conn.cursor()
    c.execute("UPDATE {} SET {} = '{}' WHERE {} = '{}'"
              .format(TABLE_NAME, ABOUTME_DESCRIBE, aboutme, ACCOUNT_ID, account_id))
    conn.commit()
    return c.rowcount == 1

def update_meals(account_id,meals):
    conn = sqlite3.connect(database_name, check_same_thread=False)
    c = conn.cursor()
    c.execute("UPDATE {} SET {} = '{}' WHERE {} = '{}'".format(TABLE_NAME,FOOD_MAP,meals,ACCOUNT_ID,account_id))
    conn.commit()
    return c.rowcount == 1

def update_profile_pic(account_id,profile_pic):
    conn = sqlite3.connect(database_name, check_same_thread=False)
    c = conn.cursor()
    c.execute("UPDATE {} SET {} = '{}' WHERE {} = '{}'".format(TABLE_NAME, PROFILE_PIC, profile_pic, ACCOUNT_ID, account_id))
    conn.commit()
    return c.rowcount == 1

def update_steps(account_id,steps):
    conn = sqlite3.connect(database_name, check_same_thread=False)
    c = conn.cursor()
    c.execute(
        "UPDATE {} SET {} = '{}' WHERE {} = '{}'".format(TABLE_NAME, STEPS, steps, ACCOUNT_ID, account_id))
    conn.commit()
    return c.rowcount == 1

def update_calories(account_id,cals):
    conn = sqlite3.connect(database_name, check_same_thread=False)
    c = conn.cursor()
    c.execute(
        "UPDATE {} SET {} = '{}' WHERE {} = '{}'".format(TABLE_NAME, CAL_BURNED, cals, ACCOUNT_ID, account_id))
    conn.commit()
    return c.rowcount == 1

def get_meals(account_id):
    conn = sqlite3.connect(database_name)
    c = conn.cursor()
    c.execute("SELECT {} FROM {} WHERE {} = {}".format(FOOD_MAP,TABLE_NAME,ACCOUNT_ID,account_id))
    data = c.fetchall()
    return json.loads(data[0][0])[0]['steps']


def get_friends(profile_name):
    conn = sqlite3.connect(database_name)
    c = conn.cursor()
    c.execute("SELECT * FROM {} WHERE {} LIKE '{}'".format(TABLE_NAME,PROFILE_NAME,profile_name))
    data = c.fetchall()
    jsonDic = []
    for d in data:
        dic = {"account_id" : d[0],
               "aboutme" : d[6],
               "profilePic" : d[8],
               "name" : d[1]}
        jsonDic.append(dic)
    return json.dumps(jsonDic)

@app.route('/data/', methods=['POST','GET'])
def data():
    account = request.args.get(ACCOUNT_ID)
    profile_name = request.args.get(PROFILE_NAME)
    profile_pic = request.args.get(PROFILE_PIC)
    aboutme = request.args.get(ABOUTME_DESCRIBE)
    cals = request.args.get(CAL_BURNED)
    steps = request.args.get(STEPS)
    #Success update message
    success = []

    print(account,profile_name)
    meals = request.args.get(FOOD_MAP)
    insert_account_id(account_id=account)

    if (cals is not None):
        s = update_calories(account, cals)
        success.append({CAL_BURNED : "Updated {}".format(s)})

    if (steps is not None):
        s = update_steps(account, steps)
        success.append({STEPS : "Updated {}".format(s)})

    if(aboutme is not None):
        s = update_aboutme(aboutme,account)
        success.append({ABOUTME_DESCRIBE : "Updated {}".format(s)})

    if profile_name is not None:
        s = update_profile_name(account_id=account,profile_name=profile_name)
        success.append({PROFILE_NAME : "Updated {}".format(s)})

    if(meals is not None):
        s = update_meals(account,meals)
        success.append({FOOD_MAP : "Updated {}".format(s)})

    if(profile_pic is not None):
        s = update_profile_pic(account,profile_pic)
        success.append({PROFILE_PIC : "Updated {}".format(s)})

    return json.dumps(success)

@app.route('/get/meals/', methods=['GET'])
def getMeals():
    account = request.args.get('account')
    return str(get_meals(account))

@app.route('/get/add_friends/')
def add_friends():
    profile_name = request.args.get(PROFILE_NAME)
    print(profile_name)
    return_msg = get_friends(profile_name=profile_name)
    print(return_msg)
    return return_msg


if __name__ == '__main__':
    onCreate()
    app.run(host='0.0.0.0', port=8080, debug=True)