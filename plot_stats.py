import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

# Lire le fichier CSV
df = pd.read_csv("output/statistics.csv")

# Extraire le nom de fichier sans le chemin
df['shortName'] = df['fileName'].apply(lambda x: x.split('/')[-1])

# Calcul de la matrice de corrélation
correlation_matrix = df[['time', 'words', 'size']].corr()
print("Matrice de corrélation :\n", correlation_matrix)

# Heatmap de la corrélation
sns.heatmap(correlation_matrix, annot=True, cmap='coolwarm', fmt=".2f")
plt.title("Matrice de corrélation")
plt.tight_layout()
plt.show()

# Nuage de points : Temps vs Taille
sns.scatterplot(data=df, x='size', y='time', hue='shortName')
plt.title("Temps vs Taille du fichier")
plt.xlabel("Taille (octets)")
plt.ylabel("Temps (ms)")
plt.grid(True)
plt.tight_layout()
plt.show()

# Nuage de points : Temps vs Nombre de mots
sns.scatterplot(data=df, x='words', y='time', hue='shortName')
plt.title("Temps vs Nombre de mots")
plt.xlabel("Nombre de mots")
plt.ylabel("Temps (ms)")
plt.grid(True)
plt.tight_layout()
plt.show()

# Nuage de points : Taille vs Nombre de mots
sns.scatterplot(data=df, x='words', y='size', hue='shortName')
plt.title("Taille vs Nombre de mots")
plt.xlabel("Nombre de mots")
plt.ylabel("Taille (octets)")
plt.grid(True)
plt.tight_layout()
plt.show()
