### Para iniciar el proyecto en git
- Abrir Git Bash
- Ejecutar git init
- Conectar al proyecto actual con: 
    - git remote add origin https://github.com/BrendaALandaC/courseflow-api.git
- Cambiar a la rama main:
  - git branch -M main
- Cambiar a la rama develop
  - git branch -M develop
  - git checkout -b develop origin/develop

### Subir nuevos cambios o actualizaciones al proyecto de git

- Entrar a la carpeta local del proyecto, en este caso:
  - cd Documents
  - cd courseflow
- Añadir los archivos con:
  - git add . 
- Añadir comentario de nueva actualizacion, ejemplo:
    - git commit -m "feat: configure database, docker and project structure"
- Subir los cambios:
- git push -u origin feature/project-setup