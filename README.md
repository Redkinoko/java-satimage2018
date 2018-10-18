# java-satimage2018 : 
## Deep learning au service du raisonnement automatique.
_Enseignant Olivier Bailleux._

Déterminer si une formule :  
   Logique sous forme normale conjonctive est cohérente (peut prendre la valeur vrai) est un problème central en raisonnement automatique. Ce problème est appelé SAT. Pour les petites formules, comme par exemple ``(a ou b)`` et ``(a ou non b)`` et ``(non a ou b)``, cela ne présente pas de difficulté particulière, mais avec des formules comportant des centaines ou milliers de variables, c’est un véritable challenge. 

L’idée à la base de ce projet est de représenter une formule CNF comme une sorte d’image :  
   ``Chaque variable correspond à une ligne``, et ``chaque clause (comme (a ou b), (non a ou b), …) à une colonne``. A l’intersection d’une ligne désignant une variable v et d’une colonne désignant une clause q, on a un ``point rouge si v appartient à q``, un ``point bleu si non v appartient à q``, ``sinon un point noir``.

La question est la suivante :  
    Après un apprentissage basé sur un très grand nombre de formules, un réseau de neurones est-il capable
de reconnaître, d’après l’image d’une formule, si cette formule est cohérente, et dans le cas contraire de
déterminer si son incohérence est difficile ou facile à établir. 
