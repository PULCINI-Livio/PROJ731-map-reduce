import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

# Charger les données
df = pd.read_csv("output/statistics.csv")

# Appliquer l’échelle racine carrée sur les tailles de fichier pour les bulles
bubble_size = np.sqrt(df["size"]) / 20  # Ajustable

plt.figure(figsize=(10, 7))
scatter = plt.scatter(
    df["time"],             # Axe X (temps)
    df["words"],            # Axe Y (nombre de mots)
    s=bubble_size,
    c=df["size"],
    cmap="plasma",
    alpha=0.7,
    edgecolors="black"
)

plt.xscale("log")
plt.yscale("log")

plt.xlabel("Temps de traitement (ms, échelle log)")
plt.ylabel("Nombre de mots différents (échelle log)")
plt.title("Bubble chart : Taille fichier vs Temps vs Vocabulaire")

cbar = plt.colorbar(scatter)
cbar.set_label("Taille du fichier (octets)")

# Afficher les noms des fichiers
for i in range(len(df)):
    plt.text(df["time"][i], df["words"][i], df["fileName"][i].split("/")[-1],
             fontsize=8, alpha=0.75)

plt.grid(True, which="both", ls="--", lw=0.5)
plt.tight_layout()
plt.show()
