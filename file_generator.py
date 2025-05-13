def generate_large_txt_file(file_path, target_size_gb=1):
    line = "Ceci est une ligne de texte répétée pour remplir le fichier.\n"
    line_size = len(line.encode('utf-8'))
    target_size_bytes = target_size_gb * 1024 * 1024 * 1024
    repetitions = target_size_bytes // line_size

    with open(file_path, 'w', encoding='utf-8') as f:
        for _ in range(int(repetitions)):
            f.write(line)

    print(f"Fichier généré : {file_path} (~{target_size_gb} Go)")

# Utilisation
generate_large_txt_file("data/fichier_1Go.txt")
