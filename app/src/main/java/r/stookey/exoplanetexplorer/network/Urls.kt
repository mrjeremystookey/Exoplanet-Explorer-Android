package r.stookey.exoplanetexplorer.network

object Urls {
    private const val PARAMETERS = "hostname," +
            "pl_letter,pl_controv_flag,pl_rade,pl_orbper," +
            "pl_bmasse,pl_bmassj,pl_orbeccen,pl_orbincl," +
            "sy_pnum,sy_snum,sy_mnum,cb_flag," +
            "disc_telescope,disc_instrument,disc_facility,disc_refname,disc_pubdate," +
            "disc_locale,discoverymethod,disc_year," +
            "rv_flag,pul_flag,ptv_flag," +
            "tran_flag,ast_flag,obm_flag," +
            "micro_flag,etv_flag,ima_flag,dkin_flag,"+
            "pl_orbper_reflink, pl_orbsmax, pl_orbsmax_reflink," +
            "pl_dens, pl_dens_reflink,pl_bmasse_reflink," +
            "pl_tranmid, pl_tranmid_reflink," +
            "pl_radj, pl_radj_reflink, pl_bmassj_reflink," +
            "pl_orbeccen_reflink, pl_insol, pl_insol_reflink," +
            "pl_eqt"

    const val ALL_PLANETS_URL =
        "https://exoplanetarchive.ipac.caltech.edu/TAP/sync?query=select+distinct(pl_name),$PARAMETERS%20from+%20pscomppars+order+by+pl_name+asc+&format=json"
}