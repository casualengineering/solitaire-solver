package test;

import java.util.ArrayList;
import java.util.List;

import data.SolitaireField;

public class CmdTest {
    public static void main(String args[]) {
        List<SolitaireField> fields = new ArrayList<>();
        boolean[][] start = {
                { false, false, false, true, false, false, false },
                { false, false, true, true, true, false, false },
                { false, true, true, true, true, true, false },
                { true, true, true, true, true, true, true },
                { false, true, true, true, true, true, false },
                { false, false, true, true, true, false, false },
                { false, false, false, true, false, false, false } };
        fields.add(new SolitaireField());
        long moves = 0;
        while (fields.get(fields.size() - 1).countSetPins() > 1) {
            // are any moves possible?
            if (fields.get(fields.size() - 1).getMoves().size() > 0) {
                // create new Solitaire Field from the latest field with the
                // first possible move
                SolitaireField f = new SolitaireField();

                /*
                 * All the stuff below is required to get a copy of the array
                 * values. Arrays.copyOf does not work for 2-dim arrays. Better
                 * solution might be possible with only one for-loop and copying
                 * of one dimensional arrays. Creating two additional arrays
                 * seems to be redundant, but it turns out to be much faster
                 * than a direct copy of every single value from one
                 * SolitaireField to the other.
                 */
                boolean[][] arr = new boolean[7][7];
                boolean[][] arrOld = fields.get(fields.size() - 1).getPins();
                for (int i = 0; i < 7; i++) {
                    for (int j = 0; j < 7; j++) {
                        arr[i][j] = arrOld[i][j];
                    }
                }
                f.setPins(arr);

                // get first possible move
                int[] move = fields.get(fields.size() - 1).getMoves().get(0);
                f.setPin(move[2], move[3]);
                f.removePin(move[0], move[1]);
                f.removePin((move[0] + move[2]) / 2, (move[1] + move[3]) / 2);
                fields.add(f);
                moves++;
            } else {
                // no more move possible -> go one step back and remove the
                // previously used move
                fields.remove(fields.size() - 1);
                fields.get(fields.size() - 1).removeMove(0);
                moves++;
            }
        }

        // print result
        System.out.println("Solution Found!");
        System.out.println("Performed Moves: " + moves);
        for (SolitaireField f : fields) {
            System.out.println(f);
        }
    }
}
