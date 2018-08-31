package com.example.asto_nhi.ranking;

import android.icu.util.ChineseCalendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import java.util.Calendar;

public class QReki {
    //QReki.RokuYo(西暦年,月,日)="旧暦年.月.日 六曜"

    // 1999-2030 Old2New パラメータ

    static int nyymin = 1999;
    static int nyymax = 2030;

    static int o2ntbl[][] = {{611,2350}	,{468,3222}	,{316,7317}	,{559,3402}	,{416,3493}
            ,{288,2901}	,{520,1388}	,{384,5467}	,{637,605}	,{494,2349}	,{343,6443}
            ,{585,2709}	,{442,2890}	,{302,5962}	,{533,2901}	,{412,2741}	,{650,1210}
            ,{507,2651}	,{369,2647}	,{611,1323}	,{468,2709}	,{329,5781}	,{559,1706}
            ,{416,2773}	,{288,2741}	,{533,1206}	,{383,5294}	,{624,2647}	,{494,1319}
            ,{356,3366}	,{572,3475}	,{442,1450}};

    static int nmdays[] = {31,28,31,30,31,30,31,31,30,31,30,31};
    static int otbl[][]=new int[14][2];
    static String uruu;//   ・・・ uruu : "閏" or "";
    static int oyy,omm,odd,DofNyy;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String RokuYo(int nyy, int nmm, int ndd){
        //get_N2O(nyy,nmm,ndd);	// 年、月、日、閏　の順に答えが返る。public
        String Rokuyou[] = {"大安","赤口","先勝","友引","先負","仏滅"};
        String ANS="";
        Calendar calendar = Calendar.getInstance();
        calendar.set(nyy,nmm-1,ndd);  // set calendar start at first of month
        ChineseCalendar chineseCalendar = new ChineseCalendar( calendar.getTime());
        int mLunarDay = chineseCalendar.get(chineseCalendar.DATE);
        int mLunarMonth = chineseCalendar.get(chineseCalendar.MONTH)+1;
        /*
        if (oyy > 0) {

            ANS = "旧暦"+oyy+"."+((omm<10)?"0":"")+omm+"."+((odd<10)?"0":"")+odd+" "+Rokuyou[(omm + odd) % 6];
        } else {
//			ANS1.value = '変換不能。入力をチェック！';
            ANS = "変換不能。入力をチェック！";
        }*/
        return Rokuyou[(mLunarMonth + mLunarDay) % 6];
    }
    // 新暦→旧暦変換
// 戻り値は Array(nyy,nmm,ndd,uruu)   ・・・ uruu : "閏" or "";
// Errorなら、 Array(0,0,0,"") を返す
    static void get_N2O(int nyy,int nmm,int ndd) {
        DofNyy = NumberDays(nyy,nmm,ndd);
        oyy = nyy;
        TblExpand(oyy);
        if (DofNyy < otbl[0][0]) {
            oyy--;
            DofNyy += 365 + LeapYear(oyy);
            TblExpand(oyy);
        }

        for (int i = 12; i >= 0 ; i--) {
            if (otbl[i][1] != 0) {
                if (otbl[i][0] <= DofNyy) {
                    omm = otbl[i][1];
                    odd = DofNyy - otbl[i][0] + 1;
                    break;
                }
            }
        }
        if (omm < 0) {
            uruu = "閏";
            omm = -omm;
        } else {
            uruu = "";
        }
    }

    // グレゴリウス暦閏年判定。閏年なら 1, 平年なら 0
//function LeapYear(yy) {
    static int LeapYear(int yy) {
        int ans = 0;
        if ((yy % 4) == 0) ans = 1;
        if ((yy % 100) == 0) ans = 0;
        if ((yy % 400) == 0) ans = 1;
        return ans;
    }

    // 新暦年初からの通日 1/1 = 1
    static int NumberDays(int yy,int mm,int dd) {
        nmdays[1] = 28 + LeapYear(yy);
        int days = dd;
        for (int m = 1; m < mm ; m++) {
            days += nmdays[m - 1];
        }
        return days;
    }

    // 旧暦・新暦テーブル(otbl)作成
    static void TblExpand(int yy) {
        int ommax,days,uruu,bit;

        days = o2ntbl[yy - nyymin][0];
        bit  = o2ntbl[yy - nyymin][1];
        uruu = days % 13;	// 閏月抽出。無ければ 0
        days = (int)Math.floor(days / 13 + 0.001);	// 旧暦年初の新暦年初からの日数

        otbl[0][0] = days;otbl[0][1] = 1;	// 旧暦正月の通日と、月数
        if (uruu == 0) {
            bit *= 2;	// 閏無しなら 12ヶ月
            ommax = 12;
        } else {
            ommax = 13;
        }

//	for (var i = 1; i <= ommax; i++) {
        for (int i = 1; i <= ommax; i++) {
            otbl[i][0] = otbl[i-1][0]+29;otbl[i][1] = i+1;
            if (bit >= 4096) {
                otbl[i][0]++;	// 大の月
            }
            bit = (bit % 4096) * 2;
        }
        otbl[ommax][1] = 0;	// テーブルの終わり＆旧暦翌年年初

        if (ommax > 12) {// 閏月のある年
            for (int i = uruu + 1; i < 13 ; i++) {
                otbl[i][1] = i;
            }
            otbl[uruu][1] = -uruu;	// 識別のため閏月はマイナスで記録
        } else {
            otbl[13][0] = 0;otbl[13][1] = 0;	// 使ってないけどエラー防止で。
        }
    }
}
