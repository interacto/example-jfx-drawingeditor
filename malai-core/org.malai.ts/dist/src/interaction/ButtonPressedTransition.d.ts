import { TSTransition } from "./TSTransition";
import { OutputState } from "../src-core/fsm/OutputState";
import { InputState } from "../src-core/fsm/InputState";
export declare class ButtonPressedTransition extends TSTransition {
    constructor(srcState: OutputState<Event>, tgtState: InputState<Event>);
    accept(e: Event): boolean;
    getAcceptedEvents(): Set<string>;
    isGuardOK(event: Event): boolean;
}