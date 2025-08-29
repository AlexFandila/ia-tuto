#!/usr/bin/env python3
import os
import re
from datetime import datetime

def escape_sql(text):
    """Escapar comillas simples para SQL"""
    return text.replace("'", "''")

def extract_date_from_filename(filename):
    """Extraer fecha del nombre del archivo"""
    # Buscar patrón DD_MM_YYYY en el nombre del archivo
    match = re.search(r'(\d{1,2})_(\d{1,2})_(\d{4})', filename)
    if match:
        day, month, year = match.groups()
        return f"{year}-{month.zfill(2)}-{day.zfill(2)}"
    return None

def create_title_from_content(content):
    """Crear título basado en las primeras palabras del contenido"""
    # Tomar las primeras 50 caracteres y buscar una frase completa
    first_part = content[:100].strip()
    # Buscar el primer punto para crear un título
    sentences = first_part.split('.')
    if len(sentences) > 0 and len(sentences[0]) > 10:
        title = sentences[0].strip()
        # Limpiar caracteres especiales al inicio
        title = re.sub(r'^[^\w\s]+', '', title)
        # Truncar si es muy largo
        if len(title) > 80:
            title = title[:77] + "..."
        return title
    
    # Si no hay punto, usar las primeras palabras
    words = first_part.split()[:8]
    return " ".join(words) + ("..." if len(words) == 8 else "")

def process_diary_files(directory):
    """Procesar todos los archivos de diario"""
    sql_entries = []
    
    # Obtener todos los archivos .txt que no sean untitled
    files = []
    for filename in os.listdir(directory):
        if filename.endswith('.txt') and 'ntitled' not in filename.lower():
            files.append(filename)
    
    # Ordenar archivos por fecha
    files.sort()
    
    for filename in files:
        filepath = os.path.join(directory, filename)
        
        # Extraer fecha del nombre del archivo
        date_str = extract_date_from_filename(filename)
        if not date_str:
            print(f"No se pudo extraer fecha de: {filename}")
            continue
        
        try:
            with open(filepath, 'r', encoding='utf-8') as f:
                content = f.read().strip()
            
            if not content:
                print(f"Archivo vacío: {filename}")
                continue
            
            # Crear título
            title = create_title_from_content(content)
            
            # Escapar contenido para SQL
            escaped_title = escape_sql(title)
            escaped_content = escape_sql(content)
            
            # Crear timestamps (añadir variación en las horas)
            hour = 20 + (len(files) % 4)  # Variar entre 20:00 y 23:00
            minute = (len(sql_entries) * 15) % 60  # Variar minutos
            created_at = f"{date_str} {hour:02d}:{minute:02d}:00"
            
            # Crear entrada SQL
            sql_entry = f"('{escaped_title}', '{escaped_content}', 4, '{created_at}', '{created_at}')"
            sql_entries.append(sql_entry)
            
            print(f"Procesado: {filename} -> {date_str}")
            
        except Exception as e:
            print(f"Error procesando {filename}: {e}")
    
    return sql_entries

# Procesar archivos
directory = "/home/alex/Downloads/Diario"
entries = process_diary_files(directory)

print(f"\n-- Procesados {len(entries)} archivos de diario")
print("-- Entradas SQL para mario_dev:")
print("INSERT INTO journal_entry (title, entry, user_id, created_at, updated_at) VALUES")

# Imprimir entradas SQL en lotes de 5
for i, entry in enumerate(entries):
    if i == len(entries) - 1:
        print(entry + ";")
    else:
        print(entry + ",")
        
print(f"\n-- Total: {len(entries)} entradas generadas")