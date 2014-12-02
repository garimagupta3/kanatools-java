package mariten.kanatools.KanaConverterTests;

import mariten.kanatools.KanaConverter;
import mariten.kanatools.KanaConverterTester;
import org.junit.Test;

public class MultiOpsTest extends KanaConverterTester
{
    //{{{ testOpFormats()
    @Test
    public void testOpFormats()
    {
        // Single op (flag-style)
        int single_op_flag = KanaConverter.OP_ZENKAKU_ALPHANUMERIC_TO_HANKAKU_ALPHANUMERIC;
        this.assertConverted(single_op_flag, "Ａ", "A");

        // Multiple ops (flag-style)
        int multi_op_flags = 0;
        multi_op_flags |= KanaConverter.OP_ZENKAKU_ALPHANUMERIC_TO_HANKAKU_ALPHANUMERIC;
        multi_op_flags |= KanaConverter.OP_HANKAKU_KATAKANA_TO_ZENKAKU_KATAKANA;
        multi_op_flags |= KanaConverter.OP_COLLAPSE_HANKAKU_VOICE_MARKS;
        this.assertConverted(multi_op_flags, "Ａ", "A");

        // Single op (PHP mb_convert_kana style)
        String single_op = "a";
        this.assertConverted(single_op, "Ａ", "A");

        // Multiple ops (PHP mb_convert_kana style)
        String multi_ops = "KVa";
        this.assertConverted(multi_ops, "Ａ", "A");
    }
    //}}}


    //{{{ testMixedOps()
    @Test
    public void testMixedOps()
    {
        // han2zen kata-kata, zen2han alphanumeric
        int op_flags = 0;
        op_flags |= KanaConverter.OP_HANKAKU_KATAKANA_TO_ZENKAKU_KATAKANA;
        op_flags |= KanaConverter.OP_COLLAPSE_HANKAKU_VOICE_MARKS;
        op_flags |= KanaConverter.OP_ZENKAKU_ALPHANUMERIC_TO_HANKAKU_ALPHANUMERIC;
        this.assertConverted(op_flags,
            " !0:A^a|\"　！０：Ａ＾ａ｜”あがぱゐゔゕゝアガパヰヸヷヵヽ゛｡ｱｶﾞﾊﾟﾞ漢 #1;B_b}\\　＃１；Ｂ＿ｂ｝＼・きじぴゑゖゞキジピヱヹヶヾ゜･ｷｼﾞﾋﾟﾟ字",
            " !0:A^a|\" !0:A^a|”あがぱゐゔゕゝアガパヰヸヷヵヽ゛。アガパ゛漢 #1;B_b}\\ #1;B_b}＼・きじぴゑゖゞキジピヱヹヶヾ゜・キジピ゜字"
        );

        // zen2han hira-kata, zen2han kata-kata
        this.assertConverted("hk",
            " !0:A^a|\"　！０：Ａ＾ａ｜”あがぱゐゔゕゝアガパヰヸヷヵヽ゛｡ｱｶﾞﾊﾟﾞ漢 #1;B_b}\\　＃１；Ｂ＿ｂ｝＼・きじぴゑゖゞキジピヱヹヶヾ゜･ｷｼﾞﾋﾟﾟ字",
            " !0:A^a|\"　！０：Ａ＾ａ｜”ｱｶﾞﾊﾟｲゔゕゝｱｶﾞﾊﾟｲヸヷヵヽﾞ｡ｱｶﾞﾊﾟﾞ漢 #1;B_b}\\　＃１；Ｂ＿ｂ｝＼･ｷｼﾞﾋﾟｴゖゞｷｼﾞﾋﾟｴヹヶヾﾟ･ｷｼﾞﾋﾟﾟ字"
        );

        // han2zen hira, zen2han alpha-only
        op_flags = 0;
        op_flags |= KanaConverter.OP_HANKAKU_KATAKANA_TO_ZENKAKU_HIRAGANA;
        op_flags |= KanaConverter.OP_ZENKAKU_LETTER_TO_HANKAKU_LETTER;
        op_flags |= KanaConverter.OP_COLLAPSE_HANKAKU_VOICE_MARKS;
        this.assertConverted(op_flags,
            " !0:A^a|\"　！０：Ａ＾ａ｜”あがぱゐゔゕゝアガパヰヸヷヵヽ゛｡ｱｶﾞﾊﾟﾞ漢 #1;B_b}\\　＃１；Ｂ＿ｂ｝＼・きじぴゑゖゞキジピヱヹヶヾ゜･ｷｼﾞﾋﾟﾟ字",
            " !0:A^a|\"　！０：A＾a｜”あがぱゐゔゕゝアガパヰヸヷヵヽ゛。あがぱ゛漢 #1;B_b}\\　＃１；B＿b｝＼・きじぴゑゖゞキジピヱヹヶヾ゜・きじぴ゜字"
        );

        // zen2zen hira-kata, han2zen num-only
        op_flags = 0;
        op_flags |= KanaConverter.OP_ZENKAKU_HIRAGANA_TO_ZENKAKU_KATAKANA;
        op_flags |= KanaConverter.OP_HANKAKU_NUMBER_TO_ZENKAKU_NUMBER;
        this.assertConverted(op_flags,
            " !0:A^a|\"　！０：Ａ＾ａ｜”あがぱゐゔゕゝアガパヰヸヷヵヽ゛｡ｱｶﾞﾊﾟﾞ漢 #1;B_b}\\　＃１；Ｂ＿ｂ｝＼・きじぴゑゖゞキジピヱヹヶヾ゜･ｷｼﾞﾋﾟﾟ字",
            " !０:A^a|\"　！０：Ａ＾ａ｜”アガパヰゔゕゝアガパヰヸヷヵヽ゛｡ｱｶﾞﾊﾟﾞ漢 #１;B_b}\\　＃１；Ｂ＿ｂ｝＼・キジピヱゖゞキジピヱヹヶヾ゜･ｷｼﾞﾋﾟﾟ字"
        );

        // zen2zen kata-hira, han2zen kata-hira, han2zen alphanumeric
        op_flags = 0;
        op_flags |= KanaConverter.OP_ZENKAKU_KATAKANA_TO_ZENKAKU_HIRAGANA;
        op_flags |= KanaConverter.OP_HANKAKU_KATAKANA_TO_ZENKAKU_HIRAGANA;
        op_flags |= KanaConverter.OP_COLLAPSE_HANKAKU_VOICE_MARKS;
        op_flags |= KanaConverter.OP_HANKAKU_ALPHANUMERIC_TO_ZENKAKU_ALPHANUMERIC;
        this.assertConverted(op_flags,
            " !0:A^a|\"　！０：Ａ＾ａ｜”あがぱゐゔゕゝアガパヰヸヷヵヽ゛｡ｱｶﾞﾊﾟﾞ漢 #1;B_b}\\　＃１；Ｂ＿ｂ｝＼・きじぴゑゖゞキジピヱヹヶヾ゜･ｷｼﾞﾋﾟﾟ字",
            "　！０：Ａ＾ａ｜\"　！０：Ａ＾ａ｜”あがぱゐゔゕゝあがぱゐヸヷヵヽ゛。あがぱ゛漢　＃１；Ｂ＿ｂ｝\\　＃１；Ｂ＿ｂ｝＼・きじぴゑゖゞきじぴゑヹヶヾ゜・きじぴ゜字"
        );

        // zen2han kata, han2zen num-only, alpha-only
        op_flags = 0;
        op_flags |= KanaConverter.OP_ZENKAKU_KATAKANA_TO_HANKAKU_KATAKANA;
        op_flags |= KanaConverter.OP_HANKAKU_LETTER_TO_ZENKAKU_LETTER;
        op_flags |= KanaConverter.OP_HANKAKU_NUMBER_TO_ZENKAKU_NUMBER;
        this.assertConverted(op_flags,
            " !0:A^a|\"　！０：Ａ＾ａ｜”あがぱゐゔゕゝアガパヰヸヷヵヽ゛｡ｱｶﾞﾊﾟﾞ漢 #1;B_b}\\　＃１；Ｂ＿ｂ｝＼・きじぴゑゖゞキジピヱヹヶヾ゜･ｷｼﾞﾋﾟﾟ字",
            " !０:Ａ^ａ|\"　！０：Ａ＾ａ｜”あがぱゐゔゕゝｱｶﾞﾊﾟｲヸヷヵヽﾞ｡ｱｶﾞﾊﾟﾞ漢 #１;Ｂ_ｂ}\\　＃１；Ｂ＿ｂ｝＼･きじぴゑゖゞｷｼﾞﾋﾟｴヹヶヾﾟ･ｷｼﾞﾋﾟﾟ字"
        );
    }
    //}}}


    //{{{ testConflictingOps()
    @Test
    public void testConflictingOps()
    {
        // han2zen-kata, han2zen-hira
        int op_flags = 0;
        op_flags |= KanaConverter.OP_COLLAPSE_HANKAKU_VOICE_MARKS;
        op_flags |= KanaConverter.OP_HANKAKU_KATAKANA_TO_ZENKAKU_HIRAGANA;
        op_flags |= KanaConverter.OP_HANKAKU_KATAKANA_TO_ZENKAKU_KATAKANA;
        super.assertConverted(op_flags,
            " !0:A^a|\"　！０：Ａ＾ａ｜”あがぱゐゔゕゝアガパヰヸヷヵヽ゛｡ｱｶﾞﾊﾟﾞ漢 #1;B_b}\\　＃１；Ｂ＿ｂ｝＼・きじぴゑゖゞキジピヱヹヶヾ゜･ｷｼﾞﾋﾟﾟ字",
            " !0:A^a|\"　！０：Ａ＾ａ｜”あがぱゐゔゕゝアガパヰヸヷヵヽ゛。アガパ゛漢 #1;B_b}\\　＃１；Ｂ＿ｂ｝＼・きじぴゑゖゞキジピヱヹヶヾ゜・キジピ゜字"
        );

        // alpha, alpha-only, num-only
        super.assertConverted("arn",
            " !0:A^a|\"　！０：Ａ＾ａ｜”あがぱゐゔゕゝアガパヰヸヷヵヽ゛｡ｱｶﾞﾊﾟﾞ漢 #1;B_b}\\　＃１；Ｂ＿ｂ｝＼・きじぴゑゖゞキジピヱヹヶヾ゜･ｷｼﾞﾋﾟﾟ字",
            " !0:A^a|\" !0:A^a|”あがぱゐゔゕゝアガパヰヸヷヵヽ゛｡ｱｶﾞﾊﾟﾞ漢 #1;B_b}\\ #1;B_b}＼・きじぴゑゖゞキジピヱヹヶヾ゜･ｷｼﾞﾋﾟﾟ字"
        );

        // han2zen alpha, zen2han alpha
        super.assertConverted("aA",
            " !0:A^a|\"　！０：Ａ＾ａ｜”あがぱゐゔゕゝアガパヰヸヷヵヽ゛｡ｱｶﾞﾊﾟﾞ漢 #1;B_b}\\　＃１；Ｂ＿ｂ｝＼・きじぴゑゖゞキジピヱヹヶヾ゜･ｷｼﾞﾋﾟﾟ字",
            "　！０：Ａ＾ａ｜\" !0:A^a|”あがぱゐゔゕゝアガパヰヸヷヵヽ゛｡ｱｶﾞﾊﾟﾞ漢　＃１；Ｂ＿ｂ｝\\ #1;B_b}＼・きじぴゑゖゞキジピヱヹヶヾ゜･ｷｼﾞﾋﾟﾟ字"
        );

        // zen2zen kata-hira, zen2zen hira-kata
        super.assertConverted("Cc",
            " !0:A^a|\"　！０：Ａ＾ａ｜”あがぱゐゔゕゝアガパヰヸヷヵヽ゛｡ｱｶﾞﾊﾟﾞ漢 #1;B_b}\\　＃１；Ｂ＿ｂ｝＼・きじぴゑゖゞキジピヱヹヶヾ゜･ｷｼﾞﾋﾟﾟ字",
            " !0:A^a|\"　！０：Ａ＾ａ｜”アガパヰゔゕゝあがぱゐヸヷヵヽ゛｡ｱｶﾞﾊﾟﾞ漢 #1;B_b}\\　＃１；Ｂ＿ｂ｝＼・キジピヱゖゞきじぴゑヹヶヾ゜･ｷｼﾞﾋﾟﾟ字"
        );
    }
    //}}}
}
