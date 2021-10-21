from flask_sqlalchemy import SQLAlchemy
from marshmallow_sqlalchemy import SQLAlchemyAutoSchema

db = SQLAlchemy()

class Usuario(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    nombre = db.Column(db.String(128))
    contrasena = db.Column(db.String(128))
    correo = db.Column(db.String(128))

class Tarea(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    usuario_id = db.Column(db.Integer, db.ForeignKey("usuario.id"))
    filename = db.Column(db.String(128))
    newformat = db.Column(db.String(128))
    status = db.Column(db.String(128)) ## UPLOADED - PROCESSED
    timestamp = db.Column(db.Date)

class UsuarioSchema(SQLAlchemyAutoSchema):
    class Meta:
         model = Usuario
         include_relationships = True
         load_instance = True

class TareaSchema(SQLAlchemyAutoSchema):
    class Meta:
         model = Tarea
         include_relationships = True
         load_instance = True