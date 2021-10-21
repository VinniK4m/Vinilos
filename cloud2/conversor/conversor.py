import os


def conversion(filename, newformat):
    cadena = 'ffmpeg -i ' + str(filename) + ' ' + str(newformat)
    try:
        os.system(cadena)
        print("Conversión realizada con exito")
    except Exception as e:
        print(e)
