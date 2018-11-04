Voici les différents repos de plugins que j’ai développé pour le projet drakogia. Voici le travail de presque une année. 

Je me permets de poster mes sources car n’étant plus dans le staff et ne voyant pas d’activités sur le git de l’équipe, je pense qu’ils seraient plus utiles pour d’autres personnes :).

Tous les plugins ont été développé dans l’optique de pouvoir tout configurer via un dashboard (via mysql en outre). Vous pourrez trouver la structure de la base de données (drakogia.sql).

L’api en elle-même est découpée dans deux repos, l’un contient toutes les interfaces (utile si vous avez des dévs avec vous et que vous ne souhaitez pas qu’ils aient accès à toute l’api en même temps), et son core.

Vous trouverez ici :

-	Un système de config,
-	Un système de commande (enregistrement automatique des commandes, plus besoin de passer par le plugin.yml not bad)
-	Différents builders (heads, items, blocks etc…)
-	Un système d’inventaire
-	Un système de Loader de dépendances
-	Un système de cache de joueur
-	Un système de grade
-	Un système de permission
-	Un système de gestion de la tablist
-	Un système de communication avec une db
-	Un système de communication avec Redis (ex : Pub/Sub)
-	Un système de traduction
-	Et différents petits systèmes comme par exemple un BottleXp
-	Un système de gestion du chat
-	Un système de modération automatique (spam, insulte)
-	Un système d’économie (commandes basiques, avec un baltop)
-	Un système de donations
-	Un système de shop (la aussi basique achats/ventes etc…)
-	Et pour finir le mcp (loin d’être fini du projet en version 1.12)

Il faut savoir que j’ai été le principal (seul) développeurs de tous ses plugins, même si pour certains je me suis basé de source de mon ancien projet VulkanMc que je développais avec [rovergames](https://gitlab.com/rovergames). C’est pourquoi, dans certains fichiers il y est mentionné le pseudo de [rovergames](https://gitlab.com/rovergames). 
De plus pour le shop, j’ai mentionné le nom de Liskoh car on lui avait acheté un plugin de shop, mais finalement j’avais décidé de tout refaire, néanmoins avec son accord j’ai réutilisé certaines de ses class.

Bon dév ;) !
