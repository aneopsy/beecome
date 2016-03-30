package io.aneopsy.theis_p.beecome.ui.utils;


import io.aneopsy.theis_p.beecome.R;

public class VersionModel {
    public String name;

    public static final String[][] data ={{"Salade", "Salade de printemps", "Salade de Tomate"},{"Fillet de Poulet", "Poulet roti et frites"}, {"Cupcake", "Donut", "Eclair",
            "Froyo", "Gingerbread", "Honeycomb",
            "Icecream Sandwich", "Jelly Bean", "Kitkat", "Lollipop"},{"Jus d'Orange"},{"Sel"},{"Icecream Sandwich"}};

    public static final String[][] dlc = {{"2 Jours restants", "2 Jours restants", "3 Jours restants"},{"3 Jours restants", "2 Jours restants"},{
            "5 Jours restants",
            "8 Jours restants",
            "2 Jours restants",
            "10 Jours restants",
            "7 Jours restants",
            "1 Jours restants",
            "6 Jours restants",
            "2 Jours restants",
            "3 Jours restants",
            "9 Jours restants"}, {"2 Jours restants"}, {"320 Jours restants"}, {"20 Jours restants"}};

    public static final int[] icon = {R.drawable.ecranpoubelle,
                                      R.drawable.ecranenergie,
                                      R.drawable.ecraneau,
            R.drawable.ent,
            R.drawable.pla,
            R.drawable.des,
            R.drawable.boi,
            R.drawable.sur,
            R.drawable.con,};

    public static final int[][] icon_frigo = {{R.drawable.entree_salade, R.drawable.entree_salade_printemps, R.drawable.entree_tomates},{R.drawable.plat_ffilet, R.drawable.plat_poulet},{
            R.drawable.cupcake_red_velvet,
            R.drawable.donut,
            R.drawable.eclair,
            R.drawable.froyo,
            R.drawable.gingerbread,
            R.drawable.honeycomb,
            R.drawable.ice_cream_sandwich,
            R.drawable.jelly_bean,
            R.drawable.kitkat,
            R.drawable.lollipop},{R.drawable.froyo},{R.drawable.froyo},{R.drawable.froyo}};

    VersionModel(String name){
        this.name=name;
    }
}
