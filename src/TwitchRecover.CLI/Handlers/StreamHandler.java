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

package TwitchRecover.CLI.Handlers;

import TwitchRecover.CLI.CLIHandler;
import TwitchRecover.CLI.Enums.oType;
import TwitchRecover.Core.Compute;
import TwitchRecover.Core.Feeds;
import TwitchRecover.Core.Live;
/**
 * StreamHandler object class which
 * handles stream prompts;
 */
public class StreamHandler {
    private int option;     //Integer value which represents the user's selected option.

    /**
     * Constructor and main method
     * of the StreamHandler
     * object class.
     * @param option    Integer value representing the user's selected option.
     */
    public StreamHandler(int option){
        this.option=option;
        if(option==1){
            retrieve();
        }
    }

    /**
     * This method prompts and handles
     * the retrieval of live stream links.
     */
    private void retrieve(){
        Live live=new Live();
        System.out.print(
                  "\n\nLive stream link retrieval:"
                + "\nEnter the channel name/link: "
        );
        String response=CLIHandler.sc.next();
        // If the response contains twitch.tv/ then it means that the user inserted a link
        // So we match the url against the regex to find the channel name
        if (response.contains("twitch.tv/")) {
            response = Compute.singleRegex("twitch\\.tv\\/([a-zA-Z0-9_]*)", response);
        }
        live.setChannel(response.toLowerCase());
        Feeds feeds=live.retrieveFeeds();
        if(feeds.getFeeds().isEmpty()){
            System.out.print(
                    "\nERROR!"
                    + "\nUnable to retrieve feeds."
                    + "\nPlease make sure that the stream is live right now."
            );
        }
        else{
            int quality=CoreHandler.selectFeeds(feeds, oType.Retrieve);
            System.out.print("M3U8 URL: "+live.getFeed(quality));
        }

    }

    /**
     * This method prompts and
     * handles the downloading of
     * a live stream.
     */
    private void download(){
        Live live=new Live();
        System.out.print(
                  "\n\nLive stream downloading:"
                + "\nEnter the channel name: "
        );
        live.setChannel(CLIHandler.sc.next());
        Feeds feeds=live.retrieveFeeds();
        if(feeds.getFeeds().isEmpty()){
            System.out.print(
                      "\nERROR!"
                    + "\nUnable to retrieve feeds."
                    + "\nPlease make sure that the stream is live right now."
            );
        }
        else{
            int quality=CoreHandler.selectFeeds(feeds, oType.Download);
            System.out.print("\nDownloading stream...");
            live.download(feeds.getFeed(quality));
        }
    }
}