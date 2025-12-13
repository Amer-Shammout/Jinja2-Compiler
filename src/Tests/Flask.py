from flask import Flask, request
import math

app = Flask(__name__)

# -----------------------------
# Global variables
# -----------------------------
global counter
counter = 0


# -----------------------------
# Helper function
# -----------------------------
def add(a, b):
    return a + b


# -----------------------------
# Lambda usage
# -----------------------------
double = lambda x: x * 2


# -----------------------------
# Simple route
# -----------------------------
@app.route("/")
def index():
    global counter
    counter = counter + 1
    return "Hello World"


# -----------------------------
# Route with logic
# -----------------------------
@app.route("/calc")
def calc():
    x = 10
    y = 3
    result1 = x + y * 2
    result2 = x // y
    result3 = double(result1)
    if result3 > 10:
        value = "large"
    else:
        value = "small"
    return value


# -----------------------------
# Class example
# -----------------------------
class Counter:
    value = 0
    def inc(self):
        self.value = self.value + 1
        return self.value
    def reset(self):
        self.value = 0
        pass  # empty method


# -----------------------------
# Object usage
# -----------------------------
counter_obj = Counter()
counter_obj.inc()
counter_obj.reset()
