package cwx;

import cwx.controller.Controller;
import cwx.model.Model;
import cwx.view.View;

/**
  o              o                     o                                                                                                                                 o        o                 o   
 <|>            <|>                   <|>                                                                                                                               <|>     _<|>_              <|>  
 / \            / \                   / \                                                                                                                               < >                        / \  
 \o/            \o/    o__  __o       \o/    o__ __o     o      o     o__  __o        o      o     o__ __o     o       o       \o__ __o__ __o      o__ __o/  \o__ __o    |        o    \o__ __o    \o/  
  |              |    /v      |>       |    /v     v\   <|>    <|>   /v      |>      <|>    <|>   /v     v\   <|>     <|>       |     |     |>    /v     |    |     |>   o__/_   <|>    |     |>    |   
 < >            < >  />      //       / \  />       <\  < >    < >  />      //       < >    < >  />       <\  < >     < >      / \   / \   / \   />     / \  / \   < >   |       / \   / \   / \   < >  
  \o    o/\o    o/   \o    o/         \o/  \         /   \o    o/   \o    o/          \o    o/   \         /   |       |       \o/   \o/   \o/   \      \o/  \o/         |       \o/   \o/   \o/        
   v\  /v  v\  /v     v\  /v __o       |    o       o     v\  /v     v\  /v __o        v\  /v     o       o    o       o        |     |     |     o      |    |          o        |     |     |     o   
    <\/>    <\/>       <\/> __/>      / \   <\__ __/>      <\/>       <\/> __/>         <\/>      <\__ __/>    <\__ __/>       / \   / \   / \    <\__  / \  / \         <\__    / \   / \   / \  _<|>_ 
                                                                                         /                                                                                                              
                                                                                        o                                                                                                               
                                                                                     __/>                                                                                                               
 *
 */

public class Main {

	/**
	 * The main method where program execution begins
	 * @param args The command line arguments
	 */
	public static void main(String[] args) {
		Model model = new Model();
		Controller controller = new Controller(model);
		View view = new View(controller);
		
		model.addObserver(view);
		
		model.init();
		view.init();
	}
}
