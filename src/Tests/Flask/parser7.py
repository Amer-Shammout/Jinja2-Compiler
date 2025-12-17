import math
from mypkg.module import func, CONST

global x, y

x = 10
y = 20

a = b = c = 42
name = other.name = user.profile.name

nums = {1, 2, 3}
letters = {"a", "b", "c"}

result = obj.method(1, 2)[0].value

self_value = obj.inner().items[0].name
flag = result is not None
flag2 = nums is not None

add = lambda p, q: p + q

@decorator
def foo(a, b):
    tmp = add(a, b)
    return tmp

@ns.decorator_with_args(1, 2)
class MyClass:
    value = 0
    def inc(self):
        self.value = self.value + 1
        other.value = self.value
        a =b=self.value

def foot(x, y):
    """Triple
        double quoted
        string"""
    z = x + y * 2 // 3 - 4.5e2
    if z >= 100:
        print("Large number:", z)
    else:
        print('Small number:', z)

class MyClass:
    pass
