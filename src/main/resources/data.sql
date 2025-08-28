-- Datos de prueba para usuarios
INSERT INTO users (username, email, password, created_at, updated_at) VALUES 
('john_doe', 'john.doe@example.com', 'password123', '2024-01-15 10:00:00', '2024-01-15 10:00:00'),
('jane_smith', 'jane.smith@example.com', 'securepass456', '2024-01-16 11:30:00', '2024-01-16 11:30:00'),
('alex_wilson', 'alex.wilson@example.com', 'mypassword789', '2024-01-17 14:20:00', '2024-01-17 14:20:00');

-- Datos de prueba para entradas de diario
INSERT INTO journal_entry (title, entry, user_id, created_at, updated_at) VALUES 
('Mi primer día', 'Hoy comenzé mi nuevo trabajo como desarrollador. Me siento emocionado pero un poco nervioso. El equipo parece muy amigable y el ambiente de trabajo es genial. Espero aprender mucho y contribuir al éxito de la empresa.', 1, '2024-01-15 18:00:00', '2024-01-15 18:00:00'),

('Reflexiones sobre el fin de semana', 'Pasé un fin de semana increíble con mi familia. Fuimos de excursión a las montañas y disfrutamos de la naturaleza. Me di cuenta de lo importante que es desconectarse del trabajo y pasar tiempo de calidad con los seres queridos.', 1, '2024-01-20 20:30:00', '2024-01-20 20:30:00'),

('Desafíos en el trabajo', 'Hoy enfrenté un problema técnico complejo que me tomó varias horas resolver. Aunque fue frustrante al principio, al final logré encontrar la solución y me siento muy orgulloso del resultado. Estos desafíos me ayudan a crecer profesionalmente.', 1, '2024-01-22 19:15:00', '2024-01-22 19:15:00'),

('Nuevo proyecto personal', 'Decidí empezar a aprender un nuevo lenguaje de programación en mi tiempo libre. Aunque es desafiante, estoy disfrutando mucho el proceso de aprendizaje. Me propuse dedicar al menos una hora diaria a este proyecto.', 2, '2024-01-18 21:00:00', '2024-01-18 21:00:00'),

('Yoga y meditación', 'Hoy probé una clase de yoga por primera vez. Al principio me sentía un poco torpe, pero al final de la sesión me sentía muy relajada y en paz. Creo que voy a incorporar esta práctica a mi rutina semanal.', 2, '2024-01-21 17:45:00', '2024-01-21 17:45:00'),

('Cocinando nuevas recetas', 'Experimenté con una nueva receta de cocina internacional. Aunque no salió perfecta la primera vez, me divertí mucho en el proceso. La cocina se ha convertido en una forma de expresión creativa para mí.', 2, '2024-01-23 20:00:00', '2024-01-23 20:00:00'),

('Entrenamiento y fitness', 'Volví al gimnasio después de varias semanas de pausa. Aunque me costó trabajo, me siento bien de haber retomado mi rutina de ejercicios. La actividad física realmente mejora mi estado de ánimo y energía.', 3, '2024-01-19 07:30:00', '2024-01-19 07:30:00'),

('Lectura inspiradora', 'Terminé de leer un libro muy inspirador sobre desarrollo personal. Las ideas que presenta me han hecho reflexionar sobre mis metas y prioridades. Definitivamente voy a implementar algunos de los consejos en mi vida diaria.', 3, '2024-01-24 22:00:00', '2024-01-24 22:00:00'),

('Tiempo con amigos', 'Tuve una reunión muy agradable con mis amigos de la universidad. Hachacía mucho que no nos veíamos y fue genial ponernos al día. Me recordó la importancia de mantener las amistades a pesar de las ocupaciones diarias.', 3, '2024-01-25 23:30:00', '2024-01-25 23:30:00');