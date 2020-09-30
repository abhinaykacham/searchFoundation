// Generated by Snowball 2.0.0 - https://snowballstem.org/

package org.tartarus.snowball.ext;

import org.tartarus.snowball.Among;

/**
 * This class implements the stemming algorithm defined by a snowball script.
 * <p>
 * Generated by Snowball 2.0.0 - https://snowballstem.org/
 * </p>
 */
@SuppressWarnings("unused")
public class irishStemmer extends org.tartarus.snowball.SnowballStemmer {

    private static final long serialVersionUID = 1L;

private final static Among a_0[] = {
    new Among("b'", -1, 1),
    new Among("bh", -1, 4),
    new Among("bhf", 1, 2),
    new Among("bp", -1, 8),
    new Among("ch", -1, 5),
    new Among("d'", -1, 1),
    new Among("d'fh", 5, 2),
    new Among("dh", -1, 6),
    new Among("dt", -1, 9),
    new Among("fh", -1, 2),
    new Among("gc", -1, 5),
    new Among("gh", -1, 7),
    new Among("h-", -1, 1),
    new Among("m'", -1, 1),
    new Among("mb", -1, 4),
    new Among("mh", -1, 10),
    new Among("n-", -1, 1),
    new Among("nd", -1, 6),
    new Among("ng", -1, 7),
    new Among("ph", -1, 8),
    new Among("sh", -1, 3),
    new Among("t-", -1, 1),
    new Among("th", -1, 9),
    new Among("ts", -1, 3)
};

private final static Among a_1[] = {
    new Among("\u00EDochta", -1, 1),
    new Among("a\u00EDochta", 0, 1),
    new Among("ire", -1, 2),
    new Among("aire", 2, 2),
    new Among("abh", -1, 1),
    new Among("eabh", 4, 1),
    new Among("ibh", -1, 1),
    new Among("aibh", 6, 1),
    new Among("amh", -1, 1),
    new Among("eamh", 8, 1),
    new Among("imh", -1, 1),
    new Among("aimh", 10, 1),
    new Among("\u00EDocht", -1, 1),
    new Among("a\u00EDocht", 12, 1),
    new Among("ir\u00ED", -1, 2),
    new Among("air\u00ED", 14, 2)
};

private final static Among a_2[] = {
    new Among("\u00F3ideacha", -1, 6),
    new Among("patacha", -1, 5),
    new Among("achta", -1, 1),
    new Among("arcachta", 2, 2),
    new Among("eachta", 2, 1),
    new Among("grafa\u00EDochta", -1, 4),
    new Among("paite", -1, 5),
    new Among("ach", -1, 1),
    new Among("each", 7, 1),
    new Among("\u00F3ideach", 8, 6),
    new Among("gineach", 8, 3),
    new Among("patach", 7, 5),
    new Among("grafa\u00EDoch", -1, 4),
    new Among("pataigh", -1, 5),
    new Among("\u00F3idigh", -1, 6),
    new Among("acht\u00FAil", -1, 1),
    new Among("eacht\u00FAil", 15, 1),
    new Among("gineas", -1, 3),
    new Among("ginis", -1, 3),
    new Among("acht", -1, 1),
    new Among("arcacht", 19, 2),
    new Among("eacht", 19, 1),
    new Among("grafa\u00EDocht", -1, 4),
    new Among("arcachta\u00ED", -1, 2),
    new Among("grafa\u00EDochta\u00ED", -1, 4)
};

private final static Among a_3[] = {
    new Among("imid", -1, 1),
    new Among("aimid", 0, 1),
    new Among("\u00EDmid", -1, 1),
    new Among("a\u00EDmid", 2, 1),
    new Among("adh", -1, 2),
    new Among("eadh", 4, 2),
    new Among("faidh", -1, 1),
    new Among("fidh", -1, 1),
    new Among("\u00E1il", -1, 2),
    new Among("ain", -1, 2),
    new Among("tear", -1, 2),
    new Among("tar", -1, 2)
};

private static final char g_v[] = {17, 65, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 17, 4, 2 };

private int I_p2;
private int I_p1;
private int I_pV;


private boolean r_mark_regions() {
    // (, line 28
    I_pV = limit;
    I_p1 = limit;
    I_p2 = limit;
    // do, line 34
    int v_1 = cursor;
    lab0: {
        // (, line 34
        // gopast, line 35
        golab1: while(true)
        {
            lab2: {
                if (!(in_grouping(g_v, 97, 250)))
                {
                    break lab2;
                }
                break golab1;
            }
            if (cursor >= limit)
            {
                break lab0;
            }
            cursor++;
        }
        // setmark pV, line 35
        I_pV = cursor;
    }
    cursor = v_1;
    // do, line 37
    int v_3 = cursor;
    lab3: {
        // (, line 37
        // gopast, line 38
        golab4: while(true)
        {
            lab5: {
                if (!(in_grouping(g_v, 97, 250)))
                {
                    break lab5;
                }
                break golab4;
            }
            if (cursor >= limit)
            {
                break lab3;
            }
            cursor++;
        }
        // gopast, line 38
        golab6: while(true)
        {
            lab7: {
                if (!(out_grouping(g_v, 97, 250)))
                {
                    break lab7;
                }
                break golab6;
            }
            if (cursor >= limit)
            {
                break lab3;
            }
            cursor++;
        }
        // setmark p1, line 38
        I_p1 = cursor;
        // gopast, line 39
        golab8: while(true)
        {
            lab9: {
                if (!(in_grouping(g_v, 97, 250)))
                {
                    break lab9;
                }
                break golab8;
            }
            if (cursor >= limit)
            {
                break lab3;
            }
            cursor++;
        }
        // gopast, line 39
        golab10: while(true)
        {
            lab11: {
                if (!(out_grouping(g_v, 97, 250)))
                {
                    break lab11;
                }
                break golab10;
            }
            if (cursor >= limit)
            {
                break lab3;
            }
            cursor++;
        }
        // setmark p2, line 39
        I_p2 = cursor;
    }
    cursor = v_3;
    return true;
}

private boolean r_initial_morph() {
    int among_var;
    // (, line 43
    // [, line 44
    bra = cursor;
    // substring, line 44
    among_var = find_among(a_0);
    if (among_var == 0)
    {
        return false;
    }
    // ], line 44
    ket = cursor;
    switch (among_var) {
        case 1:
            // (, line 46
            // delete, line 46
            slice_del();
            break;
        case 2:
            // (, line 52
            // <-, line 52
            slice_from("f");
            break;
        case 3:
            // (, line 58
            // <-, line 58
            slice_from("s");
            break;
        case 4:
            // (, line 61
            // <-, line 61
            slice_from("b");
            break;
        case 5:
            // (, line 63
            // <-, line 63
            slice_from("c");
            break;
        case 6:
            // (, line 65
            // <-, line 65
            slice_from("d");
            break;
        case 7:
            // (, line 69
            // <-, line 69
            slice_from("g");
            break;
        case 8:
            // (, line 71
            // <-, line 71
            slice_from("p");
            break;
        case 9:
            // (, line 75
            // <-, line 75
            slice_from("t");
            break;
        case 10:
            // (, line 89
            // <-, line 89
            slice_from("m");
            break;
    }
    return true;
}

private boolean r_RV() {
    if (!(I_pV <= cursor))
    {
        return false;
    }
    return true;
}

private boolean r_R1() {
    if (!(I_p1 <= cursor))
    {
        return false;
    }
    return true;
}

private boolean r_R2() {
    if (!(I_p2 <= cursor))
    {
        return false;
    }
    return true;
}

private boolean r_noun_sfx() {
    int among_var;
    // (, line 103
    // [, line 104
    ket = cursor;
    // substring, line 104
    among_var = find_among_b(a_1);
    if (among_var == 0)
    {
        return false;
    }
    // ], line 104
    bra = cursor;
    switch (among_var) {
        case 1:
            // (, line 108
            // call R1, line 108
            if (!r_R1())
            {
                return false;
            }
            // delete, line 108
            slice_del();
            break;
        case 2:
            // (, line 110
            // call R2, line 110
            if (!r_R2())
            {
                return false;
            }
            // delete, line 110
            slice_del();
            break;
    }
    return true;
}

private boolean r_deriv() {
    int among_var;
    // (, line 113
    // [, line 114
    ket = cursor;
    // substring, line 114
    among_var = find_among_b(a_2);
    if (among_var == 0)
    {
        return false;
    }
    // ], line 114
    bra = cursor;
    switch (among_var) {
        case 1:
            // (, line 116
            // call R2, line 116
            if (!r_R2())
            {
                return false;
            }
            // delete, line 116
            slice_del();
            break;
        case 2:
            // (, line 118
            // <-, line 118
            slice_from("arc");
            break;
        case 3:
            // (, line 120
            // <-, line 120
            slice_from("gin");
            break;
        case 4:
            // (, line 122
            // <-, line 122
            slice_from("graf");
            break;
        case 5:
            // (, line 124
            // <-, line 124
            slice_from("paite");
            break;
        case 6:
            // (, line 126
            // <-, line 126
            slice_from("\u00F3id");
            break;
    }
    return true;
}

private boolean r_verb_sfx() {
    int among_var;
    // (, line 129
    // [, line 130
    ket = cursor;
    // substring, line 130
    among_var = find_among_b(a_3);
    if (among_var == 0)
    {
        return false;
    }
    // ], line 130
    bra = cursor;
    switch (among_var) {
        case 1:
            // (, line 133
            // call RV, line 133
            if (!r_RV())
            {
                return false;
            }
            // delete, line 133
            slice_del();
            break;
        case 2:
            // (, line 138
            // call R1, line 138
            if (!r_R1())
            {
                return false;
            }
            // delete, line 138
            slice_del();
            break;
    }
    return true;
}

public boolean stem() {
    // (, line 143
    // do, line 144
    int v_1 = cursor;
    // call initial_morph, line 144
    r_initial_morph();
    cursor = v_1;
    // do, line 145
    // call mark_regions, line 145
    r_mark_regions();
    // backwards, line 146
    limit_backward = cursor;
    cursor = limit;
    // (, line 146
    // do, line 147
    int v_3 = limit - cursor;
    // call noun_sfx, line 147
    r_noun_sfx();
    cursor = limit - v_3;
    // do, line 148
    int v_4 = limit - cursor;
    // call deriv, line 148
    r_deriv();
    cursor = limit - v_4;
    // do, line 149
    int v_5 = limit - cursor;
    // call verb_sfx, line 149
    r_verb_sfx();
    cursor = limit - v_5;
    cursor = limit_backward;
    return true;
}

@Override
public boolean equals( Object o ) {
    return o instanceof irishStemmer;
}

@Override
public int hashCode() {
    return irishStemmer.class.getName().hashCode();
}



}

