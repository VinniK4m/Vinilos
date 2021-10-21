from flask_restful import Resource

from ..modelos import db, Usuario, Tarea, TareaSchema
from flask import request
from sqlalchemy.exc import IntegrityError
from flask_jwt_extended import jwt_required, create_access_token, get_jwt_identity
from datetime import datetime
import os
# from celery import Celery


class VistaRegistro(Resource):
    def post(self):
        contrasena1 = request.json["password1"]
        contrasena2 = request.json["password2"]
        if contrasena1 == contrasena2:

            nuevo_usuario = Usuario(nombre=request.json["username"], contrasena=contrasena1,
                                    correo=request.json["email"])
            db.session.add(nuevo_usuario)
            db.session.commit()
            return {"mensaje": "usuario creado exitosamente"}
        else:
            return {"mensaje": "el usuario no se creo, clave no coincide"}


class VistaAutenticador(Resource):
    def post(self):
        u_nombre = request.json["username"]
        u_contrasena = request.json["password"]
        usuario = Usuario.query.filter_by(nombre=u_nombre, contrasena=u_contrasena).first()
        if usuario:
            token_de_acceso = create_access_token(identity=usuario.id)
            data = {'estado': 'ok', 'token': token_de_acceso}
            return data, 200
        else:
            data = {'estado': 'Nok'}
            return data, 404


class VistaTareas(Resource):
    def __init__(self):
        self.tarea_schema = TareaSchema()

    @jwt_required()
    def post(self):
        ##RECUPERA EL USUARIO A PARTIR DE JWT
        current_user_id = get_jwt_identity()

        nueva_tarea = Tarea(filename=request.json["filename"],
                            newformat=request.json["newformat"],
                            usuario_id=current_user_id,
                            timestamp=datetime.now(),
                            status="UPLOADED")
        db.session.add(nueva_tarea)
        db.session.commit()
        data = {'estado': 'La tarea se creo'}

        # CONVERSIÓN LLAMANDO A CONSOLA
        cadena = "ffmpeg -i " + str(request.json["filename"]) + " " + str(request.json["newformat"])
        os.system(str(cadena))
        return data, 200

    @jwt_required()
    def get(self):
        ##RECUPERA EL USUARIO A PARTIR DE JWT
        current_user_id = get_jwt_identity()
        print(current_user_id)

        return [self.tarea_schema.dump(ca) for ca in Tarea.query.filter_by(usuario_id=current_user_id).all()]


class VistaTarea(Resource):
    def __init__(self):
        self.tarea_schema = TareaSchema()

    @jwt_required()
    def put(self, id_task):
        tarea = Tarea.query.get_or_404(id_task)
        tarea.newformat = request.json.get("newformat", tarea.newformat)
        tarea.status = "UPLOADED"
        db.session.commit()
        return self.tarea_schema.dump(tarea)

    @jwt_required()
    def get(self, id_task):
        return TareaSchema().dump(Tarea.query.get_or_404(id_task))

    @jwt_required()
    def delete(self, id_task):
        tarea = Tarea.query.get_or_404(id_task)
        db.session.delete(tarea)
        db.session.commit()
        return 200


class VistaConversor(Resource):
    def get(self, filename):
        return 200
