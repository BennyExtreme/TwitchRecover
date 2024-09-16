/*
 * Copyright (c) 2020, 2021 Daylam Tayari <daylam@tayari.gg>
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License version 3as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not see http://www.gnu.org/licenses/ or write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 *  @author Daylam Tayari daylam@tayari.gg https://github.com/daylamtayari
 *  @version 2.0aH     2.0a Hotfix
 *  Github project home page: https://github.com/TwitchRecover
 *  Twitch Recover repository: https://github.com/TwitchRecover/TwitchRecover
 */

package TwitchRecover.CLI;

//import TwitchRecover.Core.Download;
import TwitchRecover.CLI.Enums.oType;
import TwitchRecover.CLI.Enums.vType;
import TwitchRecover.CLI.Handlers.ClipHandler;
import TwitchRecover.CLI.Handlers.HighlightHandler;
import TwitchRecover.CLI.Handlers.StreamHandler;
import TwitchRecover.CLI.Handlers.VODHandler;
import TwitchRecover.CLI.Handlers.VideoHandler;
import TwitchRecover.CLI.Prompts;
import TwitchRecover.Core.FileIO;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is the handler for the entirety of the CLI
 * version of Twitch Recover.
 */
public class CLIHandler {
    public static Scanner sc=new Scanner(System.in);    //All scanners use this scanner. TODO: Find better way, or a scanner for each independent usage but this will have to do for the alpha.
    /**
     * Core method of the CLI handler.
     */
    protected static void main(){
        Prompts.alphaDisclaimer();
        System.out.print("\n"); //TODO: To remove for the final version.
        Prompts.welcome();
        boolean goAgane=true;
        boolean repeat=false;
        while(goAgane){
            if(repeat){
                Prompts.clearConsole();
                Prompts.repeatWelcome();
            }
            int mOption=Prompts.menu();
            boolean agane=true;
            oType op;
            vType vt;
            while(agane) {
                //Call the coordinating handler object and set the vType enum variable.
                if(mOption <= 1) {      //Stream:
                    StreamHandler sh = new StreamHandler(mOption);
                    vt=vType.Stream;
                }
                else if(mOption <= 4) {     //VOD:
                    VODHandler vh = new VODHandler(mOption);
                    vt=vType.VOD;
                }
                else if(mOption <= 7) {     //Highlight:
                    HighlightHandler hh = new HighlightHandler(mOption);
                    vt=vType.Highlight;
                }
                else if(mOption <= 11) {    //Video:
                    VideoHandler vh = new VideoHandler(mOption);
                    vt=vType.Video;
                }
                else {    //Clip:
                    ClipHandler ch = new ClipHandler(mOption);
                    vt=vType.Clip;
                }
                //Set the oType enum variable correctly matching to the operation:
                if(mOption==1 || mOption==2 || mOption==5 || mOption==12){
                    op=oType.Retrieve;
                }
                else if(mOption==3 || mOption==6 || mOption==10 || mOption==13){
                    op=oType.Download;
                }
                else if(mOption==4 || mOption==7 || mOption==14){
                    op=oType.Recover;
                }
                else if(mOption==8){
                    op=oType.Check;
                }
                else if(mOption==9){
                    op=oType.Unmute;
                }
                else{
                    op=oType.Convert;
                }
                //Repeat option prompt:
                agane=Prompts.repeat(vt, op);
            }
            goAgane=Prompts.goAgane();
        }
        sc.close();
        Prompts.exitMessage();
    }
}
