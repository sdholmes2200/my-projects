#Calculator program

def add (n1, n2):
    return n1 + n2
def subtract (n1, n2):
    return n1 - n2
def multiply (n1, n2):
    return n1 * n2
def divide (n1, n2):
    if n2 == 0:
        return "INVALID"
    else: 
        return n1/n2

#main


continu = "y"

while (True):

    print ("\nSelect operation\n1. add\n2. subtract\n3. multiply\n4. divide")

    choice = input("Enter choice (1, 2, 3, 4): ")

    num1 = float(input("Enter first number: "))
    num2 = float(input("Enter second number: "))

    if choice == '1' :
        print(num1, "+", num2,"=", add(num1, num2))
    elif choice == '2':
        print(num1, "-", num2,"=", subtract(num1, num2))
    elif choice == '3':
        print(num1, "*", num2,"=", multiply(num1, num2))
    elif choice == '4':
         print(num1, "/", num2, "=" , divide(num1, num2))
    else:
        print("Invalid operation")

    continu = input("Continue? y/n: ")
    if continu == "n":
        break